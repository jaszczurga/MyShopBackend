package com.ecommerce.myshop.Exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String resourceName) {
        super(String.format(" not found with the given input data "+resourceName));
    }
}
