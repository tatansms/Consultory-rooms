package com.datos.umag.consultorioMedico.exception;

public class AppointmentAlreadyCompletedException extends RuntimeException {
    public AppointmentAlreadyCompletedException(String message) {
        super(message);
    }
}