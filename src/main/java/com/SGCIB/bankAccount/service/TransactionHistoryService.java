package com.SGCIB.bankAccount.service;

import com.SGCIB.bankAccount.domain.TransactionHistory;

import java.text.ParseException;
import java.util.List;

public interface TransactionHistoryService {

    TransactionHistory save(TransactionHistory transactionHistory);

    List<TransactionHistory> getHistoryOperationsBetweenStartAndEndDate(Integer accountNumberPayer, Integer accountNumberPayee,
                                                                        String startDate, String endDate) throws Exception;

}
