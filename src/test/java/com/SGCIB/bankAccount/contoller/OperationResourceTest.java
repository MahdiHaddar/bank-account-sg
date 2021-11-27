package com.SGCIB.bankAccount.contoller;

import com.SGCIB.bankAccount.controller.OperationResource;
import com.SGCIB.bankAccount.domain.Operation;
import com.SGCIB.bankAccount.domain.enumeration.BankOperationType;
import com.SGCIB.bankAccount.domain.enumeration.BankServiceType;
import com.SGCIB.bankAccount.service.OperationService;
import com.SGCIB.bankAccount.utils.OperationInit;
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
@WebMvcTest(OperationResource.class)
public class OperationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationService operationService;


    private static final String URL = "/operations";

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @Test
    void return_200_when_deposit_money_operation_is_created() throws Exception {
        Random random = new Random();
        Integer randomAccountNumber = Math.abs(random.nextInt());
        Long randomId = Math.abs(random.nextLong());
        Operation operationSaved = OperationInit.operation(randomAccountNumber,
            BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        operationSaved.setId(randomId);
        given(operationService.depositMoney(100F, randomAccountNumber, BankServiceType.DEPOSIT)).willReturn(operationSaved);

        mockMvc.perform(MockMvcRequestBuilders
                .post(URL + "/deposit/100F/accountNumber/" + randomAccountNumber)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void return_200_when_withdraw_money_operation_is_created() throws Exception {

        Operation operation = OperationInit.operation(00111,
            BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        Operation operationSaved = OperationInit.operation(00111,
            BankOperationType.CREDIT, BankServiceType.DEPOSIT);
        operationSaved.setId(000000L);
        given(operationService.withdrawMoney(100F, 00111, BankServiceType.DEPOSIT)).willReturn(operationSaved);

        mockMvc.perform(MockMvcRequestBuilders
                .post(URL + "/withdraw/100F/accountNumber/00111")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
