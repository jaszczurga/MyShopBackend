package com.ecommerce.myshop.Exceptions;

public class CategoryNameExistsException extends RuntimeException{

    public CategoryNameExistsException(String message) {
        super(message);
    }

    public CategoryNameExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNameExistsException(Throwable cause) {
        super(cause);
    }
}
