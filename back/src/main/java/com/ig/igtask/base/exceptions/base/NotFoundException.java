package com.ig.igtask.base.exceptions.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class NotFoundException extends Exception {
    private String errorCode;

    public NotFoundException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return this.errorCode;
    }

    public abstract String getExceptionType();
}
