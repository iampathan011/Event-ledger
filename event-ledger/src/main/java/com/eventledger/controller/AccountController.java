package com.eventledger.controller;

import com.eventledger.dto.response.BalanceResponse;
import com.eventledger.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts", description = "Account balance queries")
@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final EventService eventService;

	public AccountController(EventService eventService) {
		this.eventService = eventService;
	}

	/**
	 * GET /accounts/{accountId}/balance
	 */
	@Operation(summary = "Get account balance", description = "Returns net balance: SUM(CREDIT) - SUM(DEBIT). Always correct regardless of event arrival order.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Balance returned"),
			@ApiResponse(responseCode = "404", description = "Account not found") })
	@GetMapping("/{accountId}/balance")
	public ResponseEntity<BalanceResponse> getBalance(
			@Parameter(description = "Account ID") @PathVariable String accountId) {
		return ResponseEntity.ok(eventService.getBalance(accountId));
	}
}
