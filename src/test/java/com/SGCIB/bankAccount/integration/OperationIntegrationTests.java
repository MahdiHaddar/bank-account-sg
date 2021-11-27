package com.SGCIB.bankAccount.integration;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.domain.Operation;
import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.utils.AccountInit;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({"classpath:data.sql"})
class OperationIntegrationTests {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private static final String URL_ACCOUNTS = "/accounts";

    private static final String URL_OPERATIONS = "/operations";

    @Before
    public void setUp() {
    }

    @Test
    void deposit_money_then_create_operation_test() {

        Random random = new Random();
        Integer randomAccountNumber = Math.abs(random.nextInt());
        Account account = AccountInit.account(randomAccountNumber);
        HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);

        ResponseEntity<Account> responseEntityAccount = restTemplate.exchange(
            createURLWithPort(URL_ACCOUNTS + "/create"),
            HttpMethod.POST, entity, Account.class);
        // solde du compte avant deposit
        assertThat(responseEntityAccount.getBody().getAmount()).isEqualTo(10000.f);

        HttpEntity<String> requestEntity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Operation> responseEntityOperation = restTemplate.exchange(
            createURLWithPort(URL_OPERATIONS + "/deposit/" + 1000 + "/accountNumber/" + randomAccountNumber),
            HttpMethod.POST, requestEntity, Operation.class);

        assertThat(responseEntityOperation.getStatusCode()).isEqualTo(HttpStatus.OK);
        // solde du compte après deposit
        assertThat(responseEntityOperation.getBody().getAccount().getAmount()).isEqualTo(11000.f);
    }

    @Test
    void withdraw_money_then_create_operation_test() {

        Random random = new Random();
        Integer randomAccountNumber = Math.abs(random.nextInt());
        Account account = AccountInit.account(randomAccountNumber);
        HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);

        ResponseEntity<Account> responseEntityAccount = restTemplate.exchange(
            createURLWithPort(URL_ACCOUNTS + "/create"),
            HttpMethod.POST, entity, Account.class);
        // solde du compte avant withdraw
        assertThat(responseEntityAccount.getBody().getAmount()).isEqualTo(10000.f);

        HttpEntity<String> requestEntity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Operation> responseEntityOperation = restTemplate.exchange(
            createURLWithPort(URL_OPERATIONS + "/withdraw/" + 1000 + "/accountNumber/" + randomAccountNumber),
            HttpMethod.POST, requestEntity, Operation.class);

        assertThat(responseEntityOperation.getStatusCode()).isEqualTo(HttpStatus.OK);
        // solde du compte après withdraw
        assertThat(responseEntityOperation.getBody().getAccount().getAmount()).isEqualTo(9000.f);
    }

    @Test
    void transfer_money_then_change_amount_account_payee_payer_test() {

        // create two account
        Random randomPayee = new Random();
        Integer randomAccountNumberAccountPayee = Math.abs(randomPayee.nextInt());
        Account accountPayee = AccountInit.account(randomAccountNumberAccountPayee);
        HttpEntity<Account> entityAccountPayye = new HttpEntity<Account>(accountPayee, headers);
        ResponseEntity<Account> responseEntityAccountPayee = restTemplate.exchange(
            createURLWithPort(URL_ACCOUNTS + "/create"),
            HttpMethod.POST, entityAccountPayye, Account.class);
        // solde du compte payee avant transfer
        assertThat(responseEntityAccountPayee.getBody().getAmount()).isEqualTo(10000.f);

        Random randomPayer = new Random();
        Integer randomAccountNumberAccountPayer = Math.abs(randomPayer.nextInt());
        Account accountPayer = AccountInit.account(randomAccountNumberAccountPayer);
        HttpEntity<Account> entityAccountPayer = new HttpEntity<Account>(accountPayer, headers);
        ResponseEntity<Account> responseEntityAccountPayer = restTemplate.exchange(
            createURLWithPort(URL_ACCOUNTS + "/create"),
            HttpMethod.POST, entityAccountPayer, Account.class);
        // solde du compte payer avant transfer
        assertThat(responseEntityAccountPayer.getBody().getAmount()).isEqualTo(10000.f);


        HttpEntity<String> requestEntity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<TransactionHistory> responseEntityOperation = restTemplate.exchange(
            createURLWithPort(URL_OPERATIONS + "/transferMoney/" + randomAccountNumberAccountPayer
                + "/to/" + randomAccountNumberAccountPayee + "/" + 2500),
            HttpMethod.POST, requestEntity, TransactionHistory.class);

        assertThat(responseEntityOperation.getStatusCode()).isEqualTo(HttpStatus.OK);
        // solde du compte payee après transfer
        assertThat(responseEntityOperation.getBody().getAccountPayer().getAmount()).isEqualTo(7500.f);
        // solde du compte payer après transfer
        assertThat(responseEntityOperation.getBody().getAccountPayee().getAmount()).isEqualTo(12500.f);
        // montant transferé
        assertThat(responseEntityOperation.getBody().getOperationValue()).isEqualTo(2500.f);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + 8080 + uri;
    }
}
