package com.SGCIB.bankAccount.controller;

import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.service.TransactionHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historyTransfer")
public class TransactionHistoryResource {

    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryResource(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping("/{accountNumberPayer}/to/{accountNumberPayee}/between/{startDate}/{endDate}")
    public List<TransactionHistory> getTransferHistory(@PathVariable("accountNumberPayer") Integer accountNumberPayer,
                                                       @PathVariable("accountNumberPayee") Integer accountNumberPayee,
                                                       @PathVariable("startDate") String startDate,
                                                       @PathVariable("endDate") String endDate) throws Exception {

        return transactionHistoryService
            .getHistoryOperationsBetweenStartAndEndDate(accountNumberPayer, accountNumberPayee, startDate, endDate);
    }

}
