package com.javarush.island.pukhov.exception.service;


public class EatingServiceException extends ServiceException { //NOSONAR

    public EatingServiceException() {
    }

    public EatingServiceException(String message) {
        super(message);
    }

    public EatingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EatingServiceException(Throwable cause) {
        super(cause);
    }
}
