package com.SGCIB.bankAccount.service;

import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.repository.TransactionHistoryRepository;
import com.SGCIB.bankAccount.service.impl.TransactionHistoryServiceImpl;
import com.SGCIB.bankAccount.utils.TransactionHistoryInit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionHistoryServiceTest {

    @InjectMocks
    private TransactionHistoryServiceImpl transactionHistoryService;

    @Mock
    private TransactionHistoryRepository transactionHistoryRepository;

    private final static Integer numero_account_payee = 222222222;

    private final static Integer numero_account_payer = 33333333;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @Test
    public void save_transaction_history_test() {
        TransactionHistory transactionHistory = TransactionHistoryInit
            .transactionHistory(numero_account_payee, numero_account_payer, 200.f);
        TransactionHistory transactionHistorySaved = TransactionHistoryInit
            .transactionHistorySaved(numero_account_payee, numero_account_payer, 200.f);
        Mockito.when(transactionHistoryRepository.save(transactionHistory)).thenReturn(transactionHistorySaved);

        TransactionHistory transactionHistoryResult = transactionHistoryService.save(transactionHistory);
        assertEquals(transactionHistorySaved, transactionHistoryResult);
    }

    @Test
    public void find_history_transaction_between_two_date_test() throws Exception {
        TransactionHistory transactionHistorySaved = TransactionHistoryInit
            .transactionHistorySaved(numero_account_payee, numero_account_payer, 200.f);
        Instant startDateFormatter = formatter.parse("2021-11-10").toInstant();
        Instant endDateFormatter = formatter.parse("2021-12-10").toInstant();

        Mockito.when(transactionHistoryRepository
            .findByAccountNumberAndDateBetweenStartAndEndDate(numero_account_payer, numero_account_payee,
                startDateFormatter, endDateFormatter)).thenReturn(Collections.singletonList(transactionHistorySaved));

        List<TransactionHistory> listTransactionHistory = transactionHistoryService.getHistoryOperationsBetweenStartAndEndDate(numero_account_payer,
            numero_account_payee, "2021-11-10", "2021-12-10");

        assertEquals(listTransactionHistory, Collections.singletonList(transactionHistorySaved));
    }
}
