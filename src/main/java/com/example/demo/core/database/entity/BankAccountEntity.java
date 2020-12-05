package com.example.demo.core.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    private long id;
}
