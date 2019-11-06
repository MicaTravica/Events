package com.app.events.exception;

public class SectorExistException extends Exception {
    
    private static final long serialVersionUID = -837116719109228941L;

    public SectorExistException(String errorMessage) {
        super(errorMessage);
    }
}
