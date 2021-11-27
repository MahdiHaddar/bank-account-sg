package com.SGCIB.bankAccount.domain;

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
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "account_payee_id")
    private Account accountPayee;

    @ManyToOne()
    @JoinColumn(name = "account_payer_id")
    private Account accountPayer;

    @Column(name = "operation_value")
    private Float operationValue;

    @Column(name = "operation_date")
    @CreationTimestamp
    private Instant date;
}
