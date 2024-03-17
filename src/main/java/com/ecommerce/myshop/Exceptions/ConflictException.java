package com.ecommerce.myshop.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
    public ConflictException(String resourceName) {
        super(String.format(" already exists with the given input data "+resourceName));
    }
}


