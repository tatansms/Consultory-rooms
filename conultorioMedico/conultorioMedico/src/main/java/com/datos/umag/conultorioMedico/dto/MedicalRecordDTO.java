package com.datos.umag.conultorioMedico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MedicalRecordDTO {
    private Long id;
    @NotNull
    private Long appointmentId;
    @NotNull
    private Long patientId;
    @NotBlank
    private String patientName;
    @NotBlank
    private String diagnosis;
    private String notes;
    @NotNull
    private LocalDateTime createdAt;
}