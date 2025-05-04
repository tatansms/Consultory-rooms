package com.datos.umag.consultorioMedico.exception;

public class AppointmentCanceledException extends RuntimeException {
    public AppointmentCanceledException(String message) {
        super(message);
    }
}