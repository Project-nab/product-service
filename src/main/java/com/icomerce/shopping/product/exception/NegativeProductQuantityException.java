package com.icomerce.shopping.product.exception;

public class NegativeProductQuantityException extends Exception {
    public NegativeProductQuantityException(String error) {
        super(error);
    }
}
