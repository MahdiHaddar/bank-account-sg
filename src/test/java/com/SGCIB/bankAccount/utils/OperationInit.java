package com.SGCIB.bankAccount.utils;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.domain.Operation;
import com.SGCIB.bankAccount.domain.enumeration.BankOperationType;
import com.SGCIB.bankAccount.domain.enumeration.BankServiceType;

public class OperationInit {

    public static Operation operation(Integer accountNumber, BankOperationType operationType,
                                          BankServiceType serviceType) {
        Operation operation = new Operation();
        operation.setOperationValue(250F);
        Account account = AccountInit.accountSaved(accountNumber);
        operation.setAccount(account);
        operation.setOperationType(operationType);
        operation.setBankServiceType(serviceType);
        return operation;
    }

    public static Operation operationSaved(Integer accountNumber, BankOperationType operationType,
                                      BankServiceType serviceType) {
        Operation operationSaved = OperationInit.operation(accountNumber, operationType, serviceType);
        operationSaved.setId(89999L);
        return operationSaved;
    }

}
