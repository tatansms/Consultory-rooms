package com.datos.umag.consultorioMedico.exception.notFound;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
