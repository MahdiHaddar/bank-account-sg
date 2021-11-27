package com.SGCIB.bankAccount.service;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.repository.AccountRepository;
import com.SGCIB.bankAccount.service.impl.AccountServiceImpl;
import com.SGCIB.bankAccount.utils.AccountInit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @Test
    public void find_by_account_number_test() {
        Account accountSaved = AccountInit.accountSaved(0000);
        Mockito.when(accountRepository.findByAccountNumber(0000)).thenReturn(accountSaved);

        Account accountResult = accountService.findByAccountNumber(0000);
        assertEquals(accountSaved, accountResult);
    }

    @Test
    public void save_account_test() {
        Account account = AccountInit.account(0000);
        Account accountSaved = AccountInit.accountSaved(0000);
        Mockito.when(accountRepository.save(account)).thenReturn(accountSaved);

        Account accountResult = accountService.save(account);
        assertEquals(accountSaved, accountResult);
    }
}
