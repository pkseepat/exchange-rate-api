package com.hsbc.exchangerate.core.model.exception;

import com.hsbc.exchangerate.core.model.Status;

import lombok.Getter;

public class RestException extends Exception {

    public static final String DEFAULT_ERROR_CODE = "9999";

    @Getter
    private final Status status;

    public RestException(String errorCode, String errorMessage) {
        super("RestException");
        this.status = new Status(false, errorCode, errorMessage);
    }

    public RestException(Throwable cause) {
        super("RestException", cause);
        this.status = new Status(false, DEFAULT_ERROR_CODE, "Exception connecting to the rates api");
    }
}
