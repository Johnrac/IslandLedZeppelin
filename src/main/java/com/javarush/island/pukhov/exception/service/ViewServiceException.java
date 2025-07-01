package com.javarush.island.pukhov.exception.service;

public class ViewServiceException extends ServiceException {//NOSONAR

    public ViewServiceException() {
    }

    public ViewServiceException(String message) {
        super(message);
    }

    public ViewServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewServiceException(Throwable cause) {
        super(cause);
    }
}
