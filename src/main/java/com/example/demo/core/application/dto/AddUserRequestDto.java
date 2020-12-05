package com.example.demo.core.application.dto;

import com.example.demo.core.application.validator.UserAgeConstraint;
import com.example.demo.core.database.entity.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@ApiModel(value = "Add User Request", description = "Request model for creating User")
@Getter
@Setter
public class AddUserRequestDto {

    @ApiModelProperty(value = "User gender", example = "MALE", required = true)
    @NotNull
    private Gender gender;

    @ApiModelProperty(value = "User first name", example = "Ivanna", required = true)
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Min(18)
    @Max(100)
    @UserAgeConstraint
    private Integer age;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\t^(?=.*[8-9]+.+)(?=.*[a-zA-Z]+.*)[a8-9-zA-Z]{6,}$")
    private String password;

}
