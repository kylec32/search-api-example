package com.scaledcode.searchapi.exceptions;

public class OperatorParsingException extends RuntimeException {
    public OperatorParsingException(String message, Exception exception) {
        super(message, exception);
    }
}
