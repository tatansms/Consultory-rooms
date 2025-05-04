package com.datos.umag.consultorioMedico.exception.notFound;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
