package com.hms.exception;

public class PatientNumberNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;


    public PatientNumberNotFoundException() {
        super("Patient not found.");
        // TODO Auto-generated constructor stub
    }

    public PatientNumberNotFoundException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
}
