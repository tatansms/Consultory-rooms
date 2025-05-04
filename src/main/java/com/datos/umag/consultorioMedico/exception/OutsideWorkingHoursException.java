package com.datos.umag.consultorioMedico.exception;

public class OutsideWorkingHoursException extends RuntimeException {
    public OutsideWorkingHoursException(String message) {
        super(message);
    }
}
