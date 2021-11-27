package com.SGCIB.bankAccount.integration;

import com.SGCIB.bankAccount.domain.Account;
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
class TransactionHistoryIntegrationTests {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private static final String URL_ACCOUNTS = "/accounts";

    private static final String URL_OPERATIONS = "/operations";

    private static final String URL_HISTORY_TRANSACTION = "/historyTransfer";

    @Before
    public void setUp() {
    }

    @Test
    void get_transfer_history_between_two_account_test() {

        // create two account
        Random randomOne = new Random();
        Integer randomAccountNumberAccountOne = Math.abs(randomOne.nextInt());
        Account accountOne = AccountInit.account(randomAccountNumberAccountOne);
        HttpEntity<Account> entityAccountPayye = new HttpEntity<Account>(accountOne, headers);
        restTemplate.exchange(createURLWithPort(URL_ACCOUNTS + "/create"),
            HttpMethod.POST, entityAccountPayye, Account.class);

        Random randomTwo = new Random();
        Integer randomAccountNumberAccountTwo = Math.abs(randomTwo.nextInt());
        Account accountTwo = AccountInit.account(randomAccountNumberAccountTwo);
        HttpEntity<Account> entityAccountTwo = new HttpEntity<Account>(accountTwo, headers);
        restTemplate.exchange(createURLWithPort(URL_ACCOUNTS + "/create"),
            HttpMethod.POST, entityAccountTwo, Account.class);

        // création de deux transfers d'argent du compte 1 vers compte 2
        HttpEntity<String> requestEntityOneToTwo = new HttpEntity<>(new HttpHeaders());
        restTemplate.exchange(createURLWithPort(URL_OPERATIONS + "/transferMoney/" + randomAccountNumberAccountOne
                + "/to/" + randomAccountNumberAccountTwo + "/" + 2500),
            HttpMethod.POST, requestEntityOneToTwo, TransactionHistory.class);

        restTemplate.exchange(createURLWithPort(URL_OPERATIONS + "/transferMoney/" + randomAccountNumberAccountOne
                + "/to/" + randomAccountNumberAccountTwo + "/" + 1000),
            HttpMethod.POST, requestEntityOneToTwo, TransactionHistory.class);

        // création d'un transfer d'argent du compte 2 vers compte 1
        HttpEntity<String> requestEntityTwoToOne = new HttpEntity<>(new HttpHeaders());
        restTemplate.exchange(createURLWithPort(URL_OPERATIONS + "/transferMoney/" + randomAccountNumberAccountTwo
                + "/to/" + randomAccountNumberAccountOne + "/" + 6000),
            HttpMethod.POST, requestEntityTwoToOne, TransactionHistory.class);

        // recupération transaction history 1 => 2
        HttpEntity<String> requestEntityOneToTw = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<TransactionHistory[]> responseEntityTransactionHistoryOneToTwo = restTemplate.exchange(
            createURLWithPort(URL_HISTORY_TRANSACTION + "/" + randomAccountNumberAccountOne
                + "/to/" + randomAccountNumberAccountTwo + "/between/2021-11-01/2021-12-30"),
            HttpMethod.GET, requestEntityOneToTw, TransactionHistory[].class);

        assertThat(responseEntityTransactionHistoryOneToTwo.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntityTransactionHistoryOneToTwo.getBody().length).isEqualTo(2);

        // recupération transaction history 2 => 1
        HttpEntity<String> requestEntity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<TransactionHistory[]> responseEntityTransactionHistoryTwoToOne = restTemplate.exchange(
            createURLWithPort(URL_HISTORY_TRANSACTION + "/" + randomAccountNumberAccountTwo
                + "/to/" + randomAccountNumberAccountOne + "/between/2021-11-01/2021-12-30"),
            HttpMethod.GET, requestEntityOneToTw, TransactionHistory[].class);

        assertThat(responseEntityTransactionHistoryTwoToOne.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntityTransactionHistoryTwoToOne.getBody().length).isEqualTo(1);

    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + 8080 + uri;
    }
}
