package com.tothenew.exception;

public class ProductVariationExistException extends RuntimeException {
    public ProductVariationExistException(String message) {
        super(message);
    }
}
