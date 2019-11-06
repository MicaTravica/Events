package com.app.events.exception;

public class HallDoesntExist extends Exception {

    private static final long serialVersionUID = 3810324729646913200L;

    public HallDoesntExist(String errorMessage) {
        super(errorMessage);
    }
}
