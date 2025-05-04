package com.datos.umag.consultorioMedico.exception;

public class AppointmentStillScheduledException extends RuntimeException {
    public AppointmentStillScheduledException(String message) {
        super(message);
    }
}
