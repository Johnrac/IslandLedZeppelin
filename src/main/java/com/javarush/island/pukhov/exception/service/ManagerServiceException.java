package com.javarush.island.pukhov.exception.service;

public class ManagerServiceException extends ServiceException {//NOSONAR

    public ManagerServiceException() {
    }

    public ManagerServiceException(String message) {
        super(message);
    }

    public ManagerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerServiceException(Throwable cause) {
        super(cause);
    }
}
