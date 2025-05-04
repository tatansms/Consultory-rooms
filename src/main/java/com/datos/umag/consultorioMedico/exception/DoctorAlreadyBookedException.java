package com.datos.umag.consultorioMedico.exception;

public class DoctorAlreadyBookedException extends RuntimeException {
    public DoctorAlreadyBookedException(String message) {
        super(message);
    }
}
