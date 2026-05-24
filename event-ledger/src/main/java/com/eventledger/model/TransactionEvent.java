package com.eventledger.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "transaction_events")
public class TransactionEvent {

	@Id
	@Column(name = "event_id", nullable = false, unique = true)
	private String eventId;

	@Column(name = "account_id", nullable = false)
	private String accountId;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 10)
	private EventType type;

	@Column(name = "amount", nullable = false, precision = 19, scale = 4)
	private BigDecimal amount;

	@Column(name = "currency", nullable = false, length = 10)
	private String currency;

	@Column(name = "event_timestamp", nullable = false)
	private Instant eventTimestamp;

	@Column(name = "received_at", nullable = false)
	private Instant receivedAt;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "metadata", columnDefinition = "TEXT")
	private Map<String, Object> metadata;

	public enum EventType {
		CREDIT, DEBIT
	}

	public TransactionEvent() {
	}

	public TransactionEvent(String eventId, String accountId, EventType type, BigDecimal amount, String currency,
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

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
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

	// -------------------------------------------------------------------------
	// Builder
	// -------------------------------------------------------------------------

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String eventId;
		private String accountId;
		private EventType type;
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

		public Builder type(EventType type) {
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

		public TransactionEvent build() {
			return new TransactionEvent(eventId, accountId, type, amount, currency, eventTimestamp, receivedAt,
					metadata);
		}
	}
}
