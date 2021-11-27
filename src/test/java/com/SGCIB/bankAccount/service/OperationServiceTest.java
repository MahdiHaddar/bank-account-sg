package com.SGCIB.bankAccount.service;

import com.SGCIB.bankAccount.domain.Operation;
import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.domain.enumeration.BankOperationType;
import com.SGCIB.bankAccount.domain.enumeration.BankServiceType;
import com.SGCIB.bankAccount.repository.OperationRepository;
import com.SGCIB.bankAccount.service.impl.OperationServiceImpl;
import com.SGCIB.bankAccount.utils.OperationInit;
import com.SGCIB.bankAccount.utils.TransactionHistoryInit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class OperationServiceTest {

    @InjectMocks
    private OperationServiceImpl operationService;

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionHistoryService transactionHistoryService;

    private final static Integer numero_account_deposit_withdraw = 111111111;

    private final static Integer numero_account_payee = 222222222;

    private final static Integer numero_account_payer = 33333333;

    private final static Integer numero_account_not_found = 4444444;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setUp() {
    }

    @Test
    public void save_operation_test() {
        Operation operation = OperationInit
            .operation(numero_account_deposit_withdraw, BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        Operation operationSaved = OperationInit
            .operationSaved(numero_account_deposit_withdraw, BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        Mockito.when(operationRepository.save(operation)).thenReturn(operationSaved);

        Operation save = operationService.save(operation);
        assertEquals(operation.getAccount(), save.getAccount());
        assertEquals(operation.getOperationValue(), save.getOperationValue());
        assertEquals(operation.getOperationValue(), save.getOperationValue());
        assertEquals(operation.getOperationType(), save.getOperationType());
    }

    @Test
    public void deposit_money_modify_amount_account_test() throws Exception {
        Operation operation = OperationInit
            .operation(numero_account_deposit_withdraw, BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        Operation operationSaved = OperationInit
            .operationSaved(numero_account_deposit_withdraw, BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        Mockito.when(operationRepository.save(operation)).thenReturn(operationSaved);
        Mockito.when(accountService.findByAccountNumber(operation.getAccount().getAccountNumber())).
            thenReturn(operation.getAccount());

        // verifier la valuer du compte avant le versement
        assertEquals(Optional.of(operation.getAccount().getAmount()), Optional.of(10000.f));

        operationService.depositMoney(100.f, numero_account_deposit_withdraw, BankServiceType.DEPOSIT);
        // verifier la valuer du compte après le versement
        assertEquals(Optional.of(operation.getAccount().getAmount()), Optional.of(10100.f));

    }

    @Test(expected = Exception.class)
    public void deposit_money_into_invalid_account__should_throws_exception_test() throws Exception {
        Operation operation = OperationInit
            .operation(numero_account_deposit_withdraw, BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        Operation operationSaved = OperationInit
            .operationSaved(numero_account_deposit_withdraw, BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        Mockito.when(operationRepository.save(operation)).thenReturn(operationSaved);
        Mockito.when(accountService.findByAccountNumber(operation.getAccount().getAccountNumber())).
            thenReturn(operation.getAccount());

        // numéro de compte inexistant
        operationService.depositMoney(100.f, numero_account_not_found, BankServiceType.DEPOSIT);

    }

    @Test
    public void withdraw_money_modify_amount_account_test() throws Exception {
        Operation operation = OperationInit
            .operation(numero_account_deposit_withdraw, BankOperationType.DEBIT, BankServiceType.WITHDRAW);
        Operation operationSaved = OperationInit
            .operationSaved(numero_account_deposit_withdraw, BankOperationType.DEBIT, BankServiceType.WITHDRAW);
        Mockito.when(operationRepository.save(operation)).thenReturn(operationSaved);
        Mockito.when(accountService.findByAccountNumber(operation.getAccount().getAccountNumber())).
            thenReturn(operation.getAccount());

        // verifier la valuer du compte avant le retrait
        assertEquals(Optional.of(operation.getAccount().getAmount()), Optional.of(10000.f));

        operationService.withdrawMoney(100.f, numero_account_deposit_withdraw, BankServiceType.WITHDRAW);
        // verifier la valeur du compte après le retrait
        assertEquals(Optional.of(operation.getAccount().getAmount()), Optional.of(9900.f));

    }

    @Test(expected = Exception.class)
    public void withdraw_money_into_invalid_account__should_throws_exception_test() throws Exception {
        Operation operation = OperationInit
            .operation(numero_account_deposit_withdraw, BankOperationType.DEBIT, BankServiceType.WITHDRAW);
        Operation operationSaved = OperationInit
            .operationSaved(numero_account_deposit_withdraw, BankOperationType.DEBIT, BankServiceType.WITHDRAW);
        Mockito.when(operationRepository.save(operation)).thenReturn(operationSaved);
        Mockito.when(accountService.findByAccountNumber(operation.getAccount().getAccountNumber())).
            thenReturn(operation.getAccount());

        // numéro de compte inexistant
        operationService.depositMoney(100.f, numero_account_not_found, BankServiceType.WITHDRAW);
    }

    @Test
    public void transfert_money_modify_amount_two_account_test() throws Exception {
        Operation operationAccountPayer = OperationInit
            .operation(numero_account_payer, BankOperationType.DEBIT, BankServiceType.TRANSFER);
        Operation operationAccountPayerSaved = OperationInit
            .operationSaved(numero_account_payer, BankOperationType.DEBIT, BankServiceType.TRANSFER);

        Operation operationAccountPayee = OperationInit
            .operation(numero_account_payee, BankOperationType.CREDIT, BankServiceType.TRANSFER);
        Operation operationAccountPayeeSaved = OperationInit
            .operationSaved(numero_account_payee, BankOperationType.CREDIT, BankServiceType.TRANSFER);

        TransactionHistory transactionHistory = TransactionHistoryInit
            .transactionHistory(numero_account_payee, numero_account_payer, 250.f);

        Mockito.when(operationRepository.save(operationAccountPayer)).thenReturn(operationAccountPayerSaved);
        Mockito.when(accountService.findByAccountNumber(operationAccountPayer.getAccount().getAccountNumber())).
            thenReturn(operationAccountPayer.getAccount());

        Mockito.when(operationRepository.save(operationAccountPayee)).thenReturn(operationAccountPayeeSaved);
        Mockito.when(accountService.findByAccountNumber(operationAccountPayee.getAccount().getAccountNumber())).
            thenReturn(operationAccountPayee.getAccount());

        Mockito.when(transactionHistoryService.save(transactionHistory)).thenReturn(transactionHistory);

        // before tranfert money
        assertEquals(Optional.of(operationAccountPayer.getAccount().getAmount()), Optional.of(10000.f));
        assertEquals(Optional.of(operationAccountPayee.getAccount().getAmount()), Optional.of(10000.f));

        operationService.transferMoney(numero_account_payer, numero_account_payee, 250.f);

        // after transfert money (250)
        assertEquals(Optional.of(operationAccountPayee.getAccount().getAmount()), Optional.of(10250.f));
        assertEquals(Optional.of(operationAccountPayer.getAccount().getAmount()), Optional.of(9750.f));
    }
}
