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

@Getter
@Setter
@ApiModel(value = "User", description = "Model for updating user and responses, which contain info about users")
public class UserDto {

    @ApiModelProperty(value = "User id", example = "6546851", required = true)
    @NotNull
    private long id;

    @ApiModelProperty(value = "User gender", example = "MALE")
    private Gender gender;

    @ApiModelProperty(value = "User first name", example = "Ivanna")
    private String firstName;

    @ApiModelProperty(value = "User last name", example = "Ivanova")
    private String lastName;

    @ApiModelProperty(value = "User age", example = "18")
    @Min(18)
    @Max(100)
    @UserAgeConstraint
    private Integer age;

    @ApiModelProperty(value = "User email, must end with @gmail.com", example = "ivannaivanova@gmail.com")
    @Email
    @Pattern(regexp = "@gmail.com$")
    private String email;

    @ApiModelProperty(value = "User authority", example = "USER/ADMIN", required = true)
    @NotNull
    private Authority authority;

    @ApiModelProperty(value = "Hospital user attends", example = "id = 1, name = 'hospital 1' ")
    @NotNull
    private Hospital hospital;

    @ApiModelProperty(value = "User bank accounts", example = "id = 1, name = 'bank 1' balance = '123'")
    @NotNull
    private List<BankAccount> bankAccount;
}
