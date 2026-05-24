package com.eventledger.repository;

import com.eventledger.model.TransactionEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionEventRepository extends JpaRepository<TransactionEvent, String> {

    List<TransactionEvent> findByAccountIdOrderByEventTimestampAsc(String accountId);

    Page<TransactionEvent> findByAccountIdOrderByEventTimestampAsc(String accountId, Pageable pageable);

    @Query("""
            SELECT COALESCE(
                SUM(CASE WHEN e.type = 'CREDIT' THEN e.amount ELSE -e.amount END),
                0
            )
            FROM TransactionEvent e
            WHERE e.accountId = :accountId
            """)
    BigDecimal computeBalance(@Param("accountId") String accountId);

    long countByAccountId(String accountId);

    boolean existsByAccountId(String accountId);
}
