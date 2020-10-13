package com.ig.igtask.base.api;

import com.ig.igtask.base.exceptions.base.NoContentFoundException;
import com.ig.igtask.base.exceptions.base.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base controller created for handling some errors during request execution.
 * Each of controller extends this.
 */
@CrossOrigin
public abstract class BaseController {

    //error handlers
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> notFoundException(NotFoundException notFoundException){
        return new HashMap<>(){{
            put("ERROR", notFoundException.getExceptionType());
            put("MESSAGE", notFoundException.getMessage());
            put("ERROR_CODE", notFoundException.getErrorCode());
        }};
    };

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoContentFoundException.class)
    public List handleNoRecords() {
        return new ArrayList();
    }


}
