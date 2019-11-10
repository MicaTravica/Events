package com.app.events.exception;

public class SectorCapacityDoesntExistException extends Exception {

    private static final long serialVersionUID = 6011418441860155238L;

    public SectorCapacityDoesntExistException(String errorMessage) {
        super(errorMessage);
    }

}
