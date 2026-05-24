package com.eventledger.service;

import com.eventledger.dto.request.EventRequest;
import com.eventledger.dto.response.EventResponse;
import com.eventledger.model.TransactionEvent;
import com.eventledger.model.TransactionEvent.EventType;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EventMapper {

	public TransactionEvent toEntity(EventRequest request) {
		return TransactionEvent.builder().eventId(request.getEventId()).accountId(request.getAccountId())
				.type(EventType.valueOf(request.getType())).amount(request.getAmount())
				.currency(request.getCurrency().toUpperCase()).eventTimestamp(request.getEventTimestamp())
				.receivedAt(Instant.now()).metadata(request.getMetadata()).build();
	}

	public EventResponse toResponse(TransactionEvent event) {
		return EventResponse.builder().eventId(event.getEventId()).accountId(event.getAccountId())
				.type(event.getType().name()).amount(event.getAmount()).currency(event.getCurrency())
				.eventTimestamp(event.getEventTimestamp()).receivedAt(event.getReceivedAt())
				.metadata(event.getMetadata()).build();
	}
}
