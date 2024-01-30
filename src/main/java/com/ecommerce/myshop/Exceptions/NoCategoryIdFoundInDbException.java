package com.ecommerce.myshop.Exceptions;

public class NoCategoryIdFoundInDbException extends RuntimeException{


    public NoCategoryIdFoundInDbException(String message) {
        super(message);
    }

    public NoCategoryIdFoundInDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCategoryIdFoundInDbException(Throwable cause) {
        super(cause);
    }
}
