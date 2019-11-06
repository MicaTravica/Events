package com.app.events.exception;

public class SectorDoesntExistException extends Exception {

    private static final long serialVersionUID = 8633858442520527545L;

    public SectorDoesntExistException(String errorMessage) {
        super(errorMessage);
    }
}
