package com.eventledger.dto.response;

import java.util.List;

public class PagedEventResponse {

	private List<EventResponse> events;
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;

	public PagedEventResponse() {
	}

	public PagedEventResponse(List<EventResponse> events, int page, int size, long totalElements, int totalPages) {
		this.events = events;
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}

	public List<EventResponse> getEvents() {
		return events;
	}

	public void setEvents(List<EventResponse> events) {
		this.events = events;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private List<EventResponse> events;
		private int page;
		private int size;
		private long totalElements;
		private int totalPages;

		public Builder events(List<EventResponse> events) {
			this.events = events;
			return this;
		}

		public Builder page(int page) {
			this.page = page;
			return this;
		}

		public Builder size(int size) {
			this.size = size;
			return this;
		}

		public Builder totalElements(long totalElements) {
			this.totalElements = totalElements;
			return this;
		}

		public Builder totalPages(int totalPages) {
			this.totalPages = totalPages;
			return this;
		}

		public PagedEventResponse build() {
			return new PagedEventResponse(events, page, size, totalElements, totalPages);
		}
	}
}
