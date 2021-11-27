package com.SGCIB.bankAccount.utils;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.domain.TransactionHistory;

public class TransactionHistoryInit {

    public static TransactionHistory transactionHistory(Integer accountNumberPayee,
                                                        Integer accountNumberPayer, Float amount) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setOperationValue(amount);
        Account accountPayee = AccountInit.account(accountNumberPayee);
        accountPayee.setId(10097L);
        Account accountPayer = AccountInit.account(accountNumberPayer);
        accountPayer.setId(10098L);
        transactionHistory.setAccountPayer(accountPayer);
        transactionHistory.setAccountPayee(accountPayee);
        return transactionHistory;
    }

    public static TransactionHistory transactionHistorySaved(Integer accountNumberPayee,
                                                             Integer accountNumberPayer, Float amount) {
        TransactionHistory transactionHistory = TransactionHistoryInit
            .transactionHistory(accountNumberPayee, accountNumberPayer, amount);
        transactionHistory.setId(89999L);
        return transactionHistory;
    }

}
