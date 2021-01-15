package com.example.demo.core.application.dto;

import com.example.demo.core.application.validator.UserAgeConstraint;
import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.domain.model.BankAccount;
import com.example.demo.core.domain.model.Hospital;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@ApiModel(value = "Add user request", description = "Request model for creating user")
@Getter
@Setter
public class AddUserDto {

    @ApiModelProperty(value = "User gender", example = "MALE", required = true)
    @NotNull
    private Gender gender;

    @ApiModelProperty(value = "User first name", example = "Ivanna", required = true)
    @NotBlank
    private String firstName;

    @ApiModelProperty(value = "User last name", example = "Ivanova", required = true)
    @NotBlank
    private String lastName;

    @ApiModelProperty(value = "User age", example = "18", required = true)
    @Min(18)
    @Max(100)
    @UserAgeConstraint
    private Integer age;

    @ApiModelProperty(value = "User email, must end with @gmail.com", example = "ivannaivanova@gmail.com", required = true)
    @Email
    @Pattern(regexp = "\\w*@gmail.com\\b")
    private String email;

    @ApiModelProperty(value = "User password", example = "ivanova123", required = true)
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    private String password;

    @ApiModelProperty(value = "User authority", example = "USER/ADMIN", required = true)
    @NotNull
    private Authority authority;

    @ApiModelProperty(value = "Hospital user attends", example = "id = 1, name = 'hospital 1' ", required = true)
    @NotNull
    private Hospital hospital;

    @ApiModelProperty(value = "User bank accounts", example = "id = 1, name = 'bank 1' balance = '123'", required = true)
    @NotNull
    private List<BankAccount> bankAccount;
}
