package com.SGCIB.bankAccount.service;

import com.SGCIB.bankAccount.domain.Operation;
import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.domain.enumeration.BankServiceType;

public interface OperationService {

    Operation save(Operation operation);

    Operation depositMoney(Float operation, Integer accountNumber, BankServiceType serviceType) throws Exception;

    Operation withdrawMoney(Float moneyDeposit, Integer accountNumber, BankServiceType serviceType) throws Exception;

    TransactionHistory transferMoney(Integer accountNumberPayer, Integer accountNumberPayee,
                                     Float moneyToTransfer) throws Exception;
}
