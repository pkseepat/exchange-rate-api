package com.hsbc.exchangerate.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hsbc.exchangerate.core.model.exception.RestException;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * Status element is included in every API response from this service.
 * Created by guneg on 11/05/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ApiModel(description = "Success or failure status of the call")
public class Status {

    boolean success;
    String errorCode;
    String errorMessage;

    public Status(Exception ex) {
        this.success = false;
        this.errorCode = RestException.DEFAULT_ERROR_CODE;
        this.errorMessage = "Exception getting the rates: " + ex.getMessage();
    }
}
