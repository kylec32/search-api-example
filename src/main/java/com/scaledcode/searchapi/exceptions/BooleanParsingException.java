package com.scaledcode.searchapi.exceptions;

public class BooleanParsingException extends RuntimeException {
    public BooleanParsingException(String message, Exception exception) {
        super(message, exception);
    }
}
