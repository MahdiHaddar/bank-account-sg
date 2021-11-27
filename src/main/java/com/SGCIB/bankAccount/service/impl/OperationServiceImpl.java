package com.SGCIB.bankAccount.service.impl;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.domain.Operation;
import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.domain.enumeration.BankOperationType;
import com.SGCIB.bankAccount.domain.enumeration.BankServiceType;
import com.SGCIB.bankAccount.repository.OperationRepository;
import com.SGCIB.bankAccount.service.AccountService;
import com.SGCIB.bankAccount.service.OperationService;
import com.SGCIB.bankAccount.service.TransactionHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final TransactionHistoryService transactionHistoryService;

    public OperationServiceImpl(OperationRepository operationRepository,
                                AccountService accountService,
                                TransactionHistoryService transactionHistoryService) {
        this.operationRepository = operationRepository;
        this.accountService = accountService;
        this.transactionHistoryService = transactionHistoryService;
    }

    /**
     * save operation
     *
     * @param operation operationToSave
     * @return operation saved
     */
    public Operation save(Operation operation) {
        if (operation == null) {
            return null;
        }
        return operationRepository.save(operation);
    }

    /**
     * deposit money into account
     *
     * @param money         money to deposit
     * @param accountNumber account to deposit money
     * @return operation
     * @throws Exception
     */
    public synchronized Operation depositMoney(Float money, Integer accountNumber,
                                               BankServiceType serviceType) throws Exception {
        Account account = accountService.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new Exception("Invalid account number");
        }
        account.setAmount(account.getAmount() + money);
        accountService.save(account);
        Operation operation = new Operation();
        operation.setOperationValue(money);
        operation.setOperationType(BankOperationType.CREDIT);
        operation.setBankServiceType(serviceType);
        operation.setAccount(account);
        return operationRepository.save(operation);
    }

    /**
     * withdraw money into account
     *
     * @param money         money to withdraw
     * @param accountNumber account to withdraw money
     * @return operation
     * @throws Exception
     */
    public synchronized Operation withdrawMoney(Float money, Integer accountNumber,
                                                BankServiceType serviceType) throws Exception {
        Account account = accountService.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new Exception("Invalid account number");
        }
        account.setAmount(account.getAmount() - money);
        accountService.save(account);
        Operation operation = new Operation();
        operation.setOperationValue(money);
        operation.setOperationType(BankOperationType.DEBIT);
        operation.setBankServiceType(serviceType);
        operation.setAccount(account);
        return operationRepository.save(operation);
    }

    /**
     * transfer money between two accounts
     *
     * @param accountNumberPayer account number payer
     * @param accountNumberPayee account number payee
     * @param moneyToTransfer    money to transfer
     * @return list of operation into two account
     */
    public TransactionHistory transferMoney(Integer accountNumberPayer,
                                            Integer accountNumberPayee,
                                            Float moneyToTransfer) throws Exception {
        Operation operationDeposit = depositMoney(moneyToTransfer, accountNumberPayee, BankServiceType.TRANSFER);
        Operation operationWithdraw = withdrawMoney(moneyToTransfer, accountNumberPayer, BankServiceType.TRANSFER);
        // create transaction history
        TransactionHistory transactionHistory = new TransactionHistory();
        Account accountPayee = operationDeposit.getAccount();
        Account accountPayer = operationWithdraw.getAccount();
        transactionHistory.setAccountPayee(accountPayee);
        transactionHistory.setAccountPayer(accountPayer);
        transactionHistory.setOperationValue(moneyToTransfer);
        return transactionHistoryService.save(transactionHistory);
    }
}
