package com.example.demo.core.application.dto;

import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.domain.model.BankAccount;
import com.example.demo.core.domain.model.Hospital;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddUserDto {

    private Gender gender;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;

    private Hospital hospital;
    private List<BankAccount> bankAccount;
}
