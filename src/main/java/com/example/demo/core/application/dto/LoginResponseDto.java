package com.example.demo.core.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "Login user response", description = "Response model for login user")
public class LoginResponseDto {

    @ApiModelProperty(value = "Token for authenticated user", example = "", required = true)
    private String token;

}
