package com.javarush.island.pukhov.exception.service;

public class MovementServiceException extends ServiceException {//NOSONAR

    public MovementServiceException() {
    }

    public MovementServiceException(String message) {
        super(message);
    }

    public MovementServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovementServiceException(Throwable cause) {
        super(cause);
    }
}
