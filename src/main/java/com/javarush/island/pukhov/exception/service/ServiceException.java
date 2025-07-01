package com.javarush.island.pukhov.exception.service;

import com.javarush.island.pukhov.exception.ApplicationException;

public class ServiceException extends ApplicationException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
