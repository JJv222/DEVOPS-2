package com.piis.cinema.domain.Exception;

public class ObjectAlreadyExistException extends RuntimeException {

    public ObjectAlreadyExistException(final String message) {
        super(message);
    }
}
