package com.datos.umag.consultorioMedico.dto.request;



import java.time.LocalTime;

public record DoctorDtoRequest(String fullName,
                               String email,
                               String speciality,
                               LocalTime availableFrom,
                               LocalTime availableTo) {
}
