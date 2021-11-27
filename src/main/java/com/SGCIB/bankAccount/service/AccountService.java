package com.SGCIB.bankAccount.service;

import com.SGCIB.bankAccount.domain.Account;

public interface AccountService {

    Account findByAccountNumber(Integer accountNumber);

    Account save(Account account);
}
