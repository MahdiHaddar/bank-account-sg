package com.SGCIB.bankAccount.repository;

import com.SGCIB.bankAccount.domain.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    /**
     * find transaction between two account
     *
     * @param accountNumberPayer accountNumberPayer
     * @param accountNumberPayee accountNumberPayee
     * @param startDate          startDate
     * @param endDate            endDate
     * @return list operation between two account
     */
    @Query(value = "Select t FROM TransactionHistory t WHERE t.accountPayee.accountNumber = :accountNumberPayee AND" +
        " t.accountPayer.accountNumber = :accountNumberPayer AND t.date between :startDate AND :endDate")
    List<TransactionHistory> findByAccountNumberAndDateBetweenStartAndEndDate(@Param("accountNumberPayer") Integer accountNumberPayer,
                                                                              @Param("accountNumberPayee") Integer accountNumberPayee,
                                                                              @Param("startDate") Instant startDate,
                                                                              @Param("endDate") Instant endDate);
}
