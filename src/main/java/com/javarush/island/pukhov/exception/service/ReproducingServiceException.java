package com.javarush.island.pukhov.exception.service;

public class ReproducingServiceException extends ServiceException {//NOSONAR

    public ReproducingServiceException() {
    }

    public ReproducingServiceException(String message) {
        super(message);
    }

    public ReproducingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReproducingServiceException(Throwable cause) {
        super(cause);
    }
}
