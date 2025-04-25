package com.datos.umag.conultorioMedico.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private String fullName;
    private String email;
    private String specialty;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}
