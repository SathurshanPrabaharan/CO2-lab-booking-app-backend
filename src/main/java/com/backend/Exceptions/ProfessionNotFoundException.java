package com.backend.Exceptions;

public class ProfessionNotFoundException extends RuntimeException {

    public ProfessionNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
