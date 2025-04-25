package com.datos.umag.conultorioMedico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ConsultRoomDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String floor;
    private String description;
}