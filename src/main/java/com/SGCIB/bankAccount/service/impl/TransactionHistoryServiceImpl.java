package com.SGCIB.bankAccount.service.impl;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.repository.TransactionHistoryRepository;
import com.SGCIB.bankAccount.service.AccountService;
import com.SGCIB.bankAccount.service.TransactionHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final AccountService accountService;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository,
                                         AccountService accountService) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.accountService = accountService;
    }

    /**
     * save transaction history
     *
     * @param transaction transaction to save
     * @return transaction saved
     */
    public TransactionHistory save(TransactionHistory transaction) {
        if (transaction == null) {
            return null;
        }
        return transactionHistoryRepository.save(transaction);
    }

    /**
     * find transaction between two account
     *
     * @param accountNumberPayer accountNumberPayer
     * @param accountNumberPayee accountNumberPayee
     * @param startDate          startDate
     * @param endDate            endDate
     * @return list operation between two account
     */
    public List<TransactionHistory> getHistoryOperationsBetweenStartAndEndDate(Integer accountNumberPayer,
                                                                               Integer accountNumberPayee,
                                                                               String startDate,
                                                                               String endDate) throws Exception {
        Instant startDateFormatter = formatter.parse(startDate).toInstant();
        Instant endDateFormatter = formatter.parse(endDate).toInstant();
        Account accountPayer = accountService.findByAccountNumber(accountNumberPayer);
        if (accountPayer == null) {
            throw new Exception("Invalid account number payer");
        }
        Account accountPayee = accountService.findByAccountNumber(accountNumberPayee);
        if (accountPayee == null) {
            throw new Exception("Invalid account number payee");
        }
        return transactionHistoryRepository
            .findByAccountNumberAndDateBetweenStartAndEndDate(accountNumberPayer, accountNumberPayee, startDateFormatter, endDateFormatter);

    }
}
