package com.ig.igtask.base.exceptions.concrete;

import com.ig.igtask.base.exceptions.base.NotFoundException;

public class NotFoundStockException extends NotFoundException {

    public NotFoundStockException(String errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public String getExceptionType() {
        return "NOT_FOUND_STOCK";
    }
}
