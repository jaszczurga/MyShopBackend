package com.ecommerce.myshop.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(String resourceName) {
        super(String.format(" invalid input data "+resourceName));
    }
}
