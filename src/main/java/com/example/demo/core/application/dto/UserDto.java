package com.example.demo.core.application.dto;

import com.example.demo.core.database.entity.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private long id;
    private Gender gender;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
}
