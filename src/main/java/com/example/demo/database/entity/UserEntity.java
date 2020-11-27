package com.example.demo.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "demo_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email", unique=true)
    private String email;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private HospitalEntity hospital;

    @OneToMany
    private List<BankAccountEntity> bankAccount;
}
