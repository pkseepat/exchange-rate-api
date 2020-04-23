package com.hsbc.exchangerate.core.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * API response with the common elements and an abstract data element
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Responses use a standard format of status and data")
public class RatesApiResponse<T> {

    private Status status;
    private T data;
}
