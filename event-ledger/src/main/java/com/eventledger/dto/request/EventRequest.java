package com.eventledger.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class EventRequest {

	@NotBlank(message = "eventId is required")
	private String eventId;

	@NotBlank(message = "accountId is required")
	private String accountId;

	@NotBlank(message = "type is required")
	@Pattern(regexp = "CREDIT|DEBIT", message = "type must be CREDIT or DEBIT")
	private String type;

	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than 0")
	private BigDecimal amount;

	@NotBlank(message = "currency is required")
	private String currency;

	@NotNull(message = "eventTimestamp is required")
	private Instant eventTimestamp;

	private Map<String, Object> metadata;

	public EventRequest() {
	}

	public EventRequest(String eventId, String accountId, String type, BigDecimal amount, String currency,
			Instant eventTimestamp, Map<String, Object> metadata) {
		this.eventId = eventId;
		this.accountId = accountId;
		this.type = type;
		this.amount = amount;
		this.currency = currency;
		this.eventTimestamp = eventTimestamp;
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

		public Builder metadata(Map<String, Object> metadata) {
			this.metadata = metadata;
			return this;
		}

		public EventRequest build() {
			return new EventRequest(eventId, accountId, type, amount, currency, eventTimestamp, metadata);
		}
	}
}
