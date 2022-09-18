package com.event.qr.backend.eventQR.exception;

public class AlreadyProcessedException extends Exception {

    public AlreadyProcessedException(String errorMessage) {
        super(errorMessage);
    }
}
