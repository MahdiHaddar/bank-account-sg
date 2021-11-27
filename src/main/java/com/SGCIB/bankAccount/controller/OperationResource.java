package com.SGCIB.bankAccount.controller;

import com.SGCIB.bankAccount.domain.Operation;
import com.SGCIB.bankAccount.domain.TransactionHistory;
import com.SGCIB.bankAccount.domain.enumeration.BankServiceType;
import com.SGCIB.bankAccount.service.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operations")
public class OperationResource {

    private final OperationService operationService;

    public OperationResource(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(path = "/deposit/{moneyDeposit}/accountNumber/{accountNumber}")
    public ResponseEntity<Operation> depositMoney(@PathVariable Float moneyDeposit,
                                                  @PathVariable Integer accountNumber) throws Exception {
        return ResponseEntity.ok(operationService.depositMoney(moneyDeposit, accountNumber, BankServiceType.DEPOSIT));
    }

    @PostMapping(path = "/withdraw/{moneyDeposit}/accountNumber/{accountNumber}")
    public ResponseEntity<Operation> withdrawMoney(@PathVariable Float moneyDeposit,
                                                   @PathVariable Integer accountNumber) throws Exception {
        return ResponseEntity.ok(operationService.withdrawMoney(moneyDeposit, accountNumber, BankServiceType.WITHDRAW));
    }

    @PostMapping("/transferMoney/{accountNumberPayer}/to/{accountNumberPayee}/{moneyToTransfer}")
    public ResponseEntity<TransactionHistory> transferMoney(@PathVariable("accountNumberPayer") Integer accountNumberPayer,
                                                            @PathVariable("accountNumberPayee") Integer accountNumberPayee,
                                                            @PathVariable("moneyToTransfer") Float moneyToTransfer) throws Exception {

        return ResponseEntity.ok(operationService
            .transferMoney(accountNumberPayer, accountNumberPayee, moneyToTransfer));
    }

}
