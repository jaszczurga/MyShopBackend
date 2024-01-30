package com.ecommerce.myshop.Exceptions;

public class NoSuchElementInDataBase extends RuntimeException{

    public NoSuchElementInDataBase(String message) {
        super(message);
    }

    public NoSuchElementInDataBase(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchElementInDataBase(Throwable cause) {
        super(cause);
    }
}
