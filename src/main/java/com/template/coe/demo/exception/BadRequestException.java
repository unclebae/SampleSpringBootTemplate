package com.template.coe.demo.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    public static final int ID_NOT_FOUND = 1;

    private static final long seriaVersionUID = 1L;

    int errorCode;

    public BadRequestException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
