package com.datos.umag.conultorioMedico.dto;


import com.datos.umag.conultorioMedico.util.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AppointmentDTO {
    private Long id;
    @NotNull
    private Long patientId;
    @NotNull
    private String patientName;
    @NotNull
    private Long doctorId;
    @NotNull
    private String doctorName;
    @NotNull
    private Long consultRoomId;
    @NotNull
    private String consultRoomName;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private AppointmentStatus status; // SCHEDULED, COMPLETED, CANCELED
}
