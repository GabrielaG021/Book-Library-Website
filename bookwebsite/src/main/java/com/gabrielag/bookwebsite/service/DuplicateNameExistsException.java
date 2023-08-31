package com.gabrielag.bookwebsite.service;

public class DuplicateNameExistsException extends Exception {
    public DuplicateNameExistsException(String message) {
        super(message);
    }

    public DuplicateNameExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
