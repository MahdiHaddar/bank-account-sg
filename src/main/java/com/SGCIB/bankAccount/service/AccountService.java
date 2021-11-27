package com.SGCIB.bankAccount.service;

import com.SGCIB.bankAccount.domain.Account;

import java.util.List;

public interface AccountService {

    Account findByAccountNumber(Integer accountNumber);

    Account save(Account account);
}
