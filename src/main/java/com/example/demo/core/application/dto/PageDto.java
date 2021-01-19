package com.example.demo.core.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Page", description = "Model for response with page of elements")
public class PageDto<T> {

    @ApiModelProperty(value = "Size of the page of elements", example = "15", required = true)
    private Integer size;

    @ApiModelProperty(value = "List of elements on the page", example = "[{..},{..},..,{..}]", required = true)
    private List<T> elements;
}
