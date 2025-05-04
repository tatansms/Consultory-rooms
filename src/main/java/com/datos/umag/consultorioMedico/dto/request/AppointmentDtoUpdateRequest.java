package com.datos.umag.consultorioMedico.dto.request;

import com.datos.umag.consultorioMedico.util.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentDtoUpdateRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        AppointmentStatus status
) {
}
