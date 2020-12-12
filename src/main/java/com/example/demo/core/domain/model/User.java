package com.example.demo.core.domain.model;

import com.example.demo.core.database.entity.Gender;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class User {

    private long id;
    private Gender gender;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private Hospital hospital;
    private List<BankAccount> bankAccount;
}
