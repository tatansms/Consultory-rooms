package com.datos.umag.consultorioMedico.dto.request;
import java.time.LocalDateTime;

public record MedicalRecordDtoRequest(
        Long idPatient,
        Long idAppointment,
        String diagnosis,
        String notes,
        LocalDateTime createdAt
) {}