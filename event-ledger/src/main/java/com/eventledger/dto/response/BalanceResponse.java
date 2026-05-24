package com.eventledger.dto.response;

import java.math.BigDecimal;

public class BalanceResponse {

	private String accountId;
	private BigDecimal balance;
	private String currency;
	private long eventCount;

	public BalanceResponse() {
	}

	public BalanceResponse(String accountId, BigDecimal balance, String currency, long eventCount) {
		this.accountId = accountId;
		this.balance = balance;
		this.currency = currency;
		this.eventCount = eventCount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public long getEventCount() {
		return eventCount;
	}

	public void setEventCount(long eventCount) {
		this.eventCount = eventCount;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String accountId;
		private BigDecimal balance;
		private String currency;
		private long eventCount;

		public Builder accountId(String accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder balance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}

		public Builder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public Builder eventCount(long eventCount) {
			this.eventCount = eventCount;
			return this;
		}

		public BalanceResponse build() {
			return new BalanceResponse(accountId, balance, currency, eventCount);
		}
	}
}
