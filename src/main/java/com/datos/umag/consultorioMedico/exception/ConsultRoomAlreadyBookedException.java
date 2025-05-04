package com.datos.umag.consultorioMedico.exception;

public class ConsultRoomAlreadyBookedException extends RuntimeException {
    public ConsultRoomAlreadyBookedException(String message) {
        super(message);
    }
}
