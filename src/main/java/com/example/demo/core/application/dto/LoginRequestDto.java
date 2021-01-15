package com.example.demo.core.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ApiModel(value = "Login user request", description = "Request model for login user")
public class LoginRequestDto {

    @ApiModelProperty(value = "User email must end with @gmail.com", example = "ivannaivanova@gmail.com", required = true)
    @Email
    private String email;

    @ApiModelProperty(value = "User password", example = "ivanova123", required = true)
    @NotBlank
    private String password;
}
