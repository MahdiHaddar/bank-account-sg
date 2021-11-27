package com.SGCIB.bankAccount.repository;

import com.SGCIB.bankAccount.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(Integer accountNumber);
}
