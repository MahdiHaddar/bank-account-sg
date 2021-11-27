package com.SGCIB.bankAccount.domain;

import com.SGCIB.bankAccount.domain.enumeration.BankOperationType;
import com.SGCIB.bankAccount.domain.enumeration.BankServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private BankOperationType operationType;

    @Column(name = "bank_service_type")
    @Enumerated(EnumType.STRING)
    private BankServiceType bankServiceType;

    @Column(name = "operation_value")
    private Float operationValue;

    @Column(name = "operation_date")
    @CreationTimestamp
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
