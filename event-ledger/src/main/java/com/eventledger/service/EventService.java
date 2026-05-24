package com.eventledger.service;

import com.eventledger.dto.request.EventRequest;
import com.eventledger.dto.response.BalanceResponse;
import com.eventledger.dto.response.EventResponse;
import com.eventledger.dto.response.PagedEventResponse;
import com.eventledger.exception.AccountNotFoundException;
import com.eventledger.exception.EventNotFoundException;
import com.eventledger.model.TransactionEvent;
import com.eventledger.repository.TransactionEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final TransactionEventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(TransactionEventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    /**
     * Submit a new transaction event.
     * Idempotency: same eventId returns original event with isDuplicate=true.
     * Concurrency safety: SERIALIZABLE isolation prevents double-insert.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SubmitResult submitEvent(EventRequest request) {
        log.debug("Received event submission: eventId={}", request.getEventId());

        return eventRepository.findById(request.getEventId())
                .map(existing -> {
                    log.debug("Duplicate event detected: eventId={}", request.getEventId());
                    return new SubmitResult(eventMapper.toResponse(existing), true);
                })
                .orElseGet(() -> {
                    TransactionEvent saved = eventRepository.save(eventMapper.toEntity(request));
                    log.debug("Saved new event: eventId={}", saved.getEventId());
                    return new SubmitResult(eventMapper.toResponse(saved), false);
                });
    }

    /**
     * Retrieve a single event by its ID.
     */
    @Transactional(readOnly = true)
    public EventResponse getEvent(String eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::toResponse)
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }

    /**
     * List events for an account ordered by eventTimestamp (chronological),
     * with pagination.
     */
    @Transactional(readOnly = true)
    public PagedEventResponse listEvents(String accountId, int page, int size) {
        if (!eventRepository.existsByAccountId(accountId)) {
            throw new AccountNotFoundException(accountId);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionEvent> resultPage =
                eventRepository.findByAccountIdOrderByEventTimestampAsc(accountId, pageable);

        List<EventResponse> events = resultPage.getContent().stream()
                .map(eventMapper::toResponse)
                .toList();

        return PagedEventResponse.builder()
                .events(events)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .build();
    }

    /**
     * Get net balance for an account: SUM(CREDIT) - SUM(DEBIT).
     * Correct regardless of event arrival order.
     */
    @Transactional(readOnly = true)
    public BalanceResponse getBalance(String accountId) {
        if (!eventRepository.existsByAccountId(accountId)) {
            throw new AccountNotFoundException(accountId);
        }

        BigDecimal balance = eventRepository.computeBalance(accountId);
        long eventCount = eventRepository.countByAccountId(accountId);

        String currency = eventRepository
                .findByAccountIdOrderByEventTimestampAsc(accountId)
                .stream()
                .findFirst()
                .map(TransactionEvent::getCurrency)
                .orElse("USD");

        return BalanceResponse.builder()
                .accountId(accountId)
                .balance(balance)
                .currency(currency)
                .eventCount(eventCount)
                .build();
    }

    // -------------------------------------------------------------------------
    // Result class — distinguishes fresh insert from idempotent replay
    // -------------------------------------------------------------------------

    public static class SubmitResult {

        private final EventResponse event;
        private final boolean isDuplicate;

        public SubmitResult(EventResponse event, boolean isDuplicate) {
            this.event = event;
            this.isDuplicate = isDuplicate;
        }

        public EventResponse getEvent() { return event; }
        public boolean isDuplicate() { return isDuplicate; }
    }
}
