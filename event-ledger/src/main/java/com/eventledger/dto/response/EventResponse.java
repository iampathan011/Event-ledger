package com.eventledger.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class EventResponse {

	private String eventId;
	private String accountId;
	private String type;
	private BigDecimal amount;
	private String currency;

	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	private Instant eventTimestamp;

	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
	private Instant receivedAt;

	private Map<String, Object> metadata;

	public EventResponse() {
	}

	public EventResponse(String eventId, String accountId, String type, BigDecimal amount, String currency,
			Instant eventTimestamp, Instant receivedAt, Map<String, Object> metadata) {
		this.eventId = eventId;
		this.accountId = accountId;
		this.type = type;
		this.amount = amount;
		this.currency = currency;
		this.eventTimestamp = eventTimestamp;
		this.receivedAt = receivedAt;
		this.metadata = metadata;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Instant getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(Instant eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public Instant getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(Instant receivedAt) {
		this.receivedAt = receivedAt;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String eventId;
		private String accountId;
		private String type;
		private BigDecimal amount;
		private String currency;
		private Instant eventTimestamp;
		private Instant receivedAt;
		private Map<String, Object> metadata;

		public Builder eventId(String eventId) {
			this.eventId = eventId;
			return this;
		}

		public Builder accountId(String accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public Builder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public Builder eventTimestamp(Instant eventTimestamp) {
			this.eventTimestamp = eventTimestamp;
			return this;
		}

		public Builder receivedAt(Instant receivedAt) {
			this.receivedAt = receivedAt;
			return this;
		}

		public Builder metadata(Map<String, Object> metadata) {
			this.metadata = metadata;
			return this;
		}

		public EventResponse build() {
			return new EventResponse(eventId, accountId, type, amount, currency, eventTimestamp, receivedAt, metadata);
		}
	}
}
