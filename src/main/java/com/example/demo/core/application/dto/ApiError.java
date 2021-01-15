package com.example.demo.core.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "Api error", description = "Model for information about api error")
public class ApiError {

    @ApiModelProperty(value = "Error timestamp", example = "2020-02-22 06:55:56", required = true)
    LocalDateTime timestamp;

    @ApiModelProperty(value = "Error message", example = "Wrong email", required = true)
    String message;
}
