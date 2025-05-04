package com.datos.umag.consultorioMedico.exception.notFound;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
