package com.datos.umag.consultorioMedico.exception.notFound;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
