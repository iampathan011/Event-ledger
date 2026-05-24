package com.eventledger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventledger.dto.request.EventRequest;
import com.eventledger.dto.response.EventResponse;
import com.eventledger.dto.response.PagedEventResponse;
import com.eventledger.service.EventService;
import com.eventledger.service.EventService.SubmitResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "Events", description = "Transaction event submission and retrieval")
@RestController
@RequestMapping("/events")
@Validated
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	/**
	 * POST /events Returns 201 on creation, 200 on idempotent replay.
	 */
	@Operation(summary = "Submit a transaction event", description = "Submits a financial transaction event. Duplicate eventIds are de-duplicated.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Event created"),
			@ApiResponse(responseCode = "200", description = "Duplicate event — original returned"),
			@ApiResponse(responseCode = "422", description = "Validation error") })
	@PostMapping
	public ResponseEntity<EventResponse> submitEvent(@Valid @RequestBody EventRequest request) {
		SubmitResult result = eventService.submitEvent(request);
		HttpStatus status = result.isDuplicate() ? HttpStatus.OK : HttpStatus.CREATED;
		return ResponseEntity.status(status).body(result.getEvent());
	}

	/**
	 * GET /events/{id}
	 */
	@Operation(summary = "Get a single event by ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Event found"),
			@ApiResponse(responseCode = "404", description = "Event not found") })
	@GetMapping("/{id}")
	public ResponseEntity<EventResponse> getEvent(@Parameter(description = "Event ID") @PathVariable String id) {
		return ResponseEntity.ok(eventService.getEvent(id));
	}

	/**
	 * GET /events?account={accountId}&page=0&size=20
	 */
	@Operation(summary = "List events for an account", description = "Returns events ordered by eventTimestamp (chronological). Supports pagination.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "List of events"),
			@ApiResponse(responseCode = "404", description = "Account not found") })
	@GetMapping
	public ResponseEntity<PagedEventResponse> listEvents(
			@Parameter(description = "Account ID", required = true) @RequestParam String account,

			@Parameter(description = "Zero-based page number") @RequestParam(defaultValue = "0") @Min(0) int page,

			@Parameter(description = "Page size (max 100)") @RequestParam(defaultValue = "20") @Min(1) int size) {

		int cappedSize = Math.min(size, 100);
		return ResponseEntity.ok(eventService.listEvents(account, page, cappedSize));
	}
}
