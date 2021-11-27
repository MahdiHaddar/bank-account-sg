package com.SGCIB.bankAccount.utils;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.domain.BankInformation;

public class AccountInit {

    public static Account account(Integer accountNumber) {
        BankInformation bankInformation = new BankInformation();
        bankInformation.setBank("SG");
        bankInformation.setBic("BIC");
        bankInformation.setDisplay("DISPLAY");
        bankInformation.setIban("IBAN");
        bankInformation.setKey("KEY");

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAmount(10000.f);
        account.setBankInformation(bankInformation);
        account.setFirstname("Mahdi");
        account.setLastname("HADDAR");
        return account;
    }

    public static Account accountSaved(Integer accountNumber) {
        Account accountSaved = account(accountNumber);
        accountSaved.setId(0L);
        accountSaved.getBankInformation().setId(0L);
        return accountSaved;
    }
}
