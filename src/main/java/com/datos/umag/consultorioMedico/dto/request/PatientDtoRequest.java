package com.datos.umag.consultorioMedico.dto.request;

public record PatientDtoRequest(
        String fullName,
        String email,
        String phone) {}