package com.SGCIB.bankAccount.contoller;

import com.SGCIB.bankAccount.controller.AccountResource;
import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.service.AccountService;
import com.SGCIB.bankAccount.utils.AccountInit;
import com.SGCIB.bankAccount.utils.TestUtil;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountResource.class)
public class AccountResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;


    private static final String URL = "/accounts/create";

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @Test
    void return_200_when_account_is_created() throws Exception {
        Random random = new Random();
        Integer randomAccountNumber = Math.abs(random.nextInt());
        Account account = AccountInit.account(randomAccountNumber);
        Account accountSaved = AccountInit.accountSaved(randomAccountNumber);
        given(accountService.save(any(Account.class))).willReturn(accountSaved);

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(account)))
            .andExpect(status().isOk());

    }

}
