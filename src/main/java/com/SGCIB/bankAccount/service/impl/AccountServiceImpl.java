package com.SGCIB.bankAccount.service.impl;

import com.SGCIB.bankAccount.domain.Account;
import com.SGCIB.bankAccount.repository.AccountRepository;
import com.SGCIB.bankAccount.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * find account by account number
     *
     * @param accountNumber account number
     * @return account
     */
    public Account findByAccountNumber(Integer accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    /**
     * save account
     *
     * @param account account to save
     * @return account saved
     */
    public Account save(Account account) {
        if (account == null) {
            return null;
        }
        return accountRepository.save(account);
    }

}
