package com.datos.umag.consultorioMedico.dto.response;

public record PatientDtoResponse(
        Long idPatient,
        String fullName,
        String email,
        String phone) {
}