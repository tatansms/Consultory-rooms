package com.datos.umag.conultorioMedico.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PatientDTO {
    private Long id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
}