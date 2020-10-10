package com.ig.igtask.base.exceptions.concrete;

import com.ig.igtask.base.exceptions.base.NotFoundException;

public class NotFoundUserException extends NotFoundException {

    public NotFoundUserException(String errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public String getExceptionType() {
        return "NOT_FOUND_USER";
    }
}
