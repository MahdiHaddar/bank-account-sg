package com.SGCIB.bankAccount.integration;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.utils.AccountInit;
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
class AccountInegrationTests {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    private static final String URL_ACCOUNTS = "/accounts";

    private static final String URL_OPERATIONS = "/operations";

    @Test
    void create_account_integration_test() {
        Random random = new Random();
        Integer randomAccountNumber = Math.abs(random.nextInt());
        Account account = AccountInit.account(randomAccountNumber);
        HttpEntity<Account> entity = new HttpEntity<Account>(account, headers);

        ResponseEntity<Account> response = restTemplate.exchange(
            createURLWithPort(URL_ACCOUNTS + "/create"),
            HttpMethod.POST, entity, Account.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + 8080 + uri;
    }
}
