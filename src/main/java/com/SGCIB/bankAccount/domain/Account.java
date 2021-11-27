package com.SGCIB.bankAccount.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(unique = true, nullable = false)
    private Integer accountNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private BankInformation bankInformation;

    @Column(nullable = false)
    private Float amount;
}
