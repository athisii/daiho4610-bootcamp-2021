package com.tothenew.exception;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException(final String message) {
        super(message);
    }

}
