package com.ecommerce.myshop.Exceptions;

public class RunOutOfStock extends RuntimeException{
    public RunOutOfStock(String resourceName) {
        super(String.format(" out of stock "+resourceName));
    }
}
