package com.scaledcode.searchapi.exceptions;

public class RequestParsingException extends RuntimeException{
    public RequestParsingException(String message, Exception exception) {
        super(message, exception);
    }
}
