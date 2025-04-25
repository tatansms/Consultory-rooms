package com.datos.umag.conultorioMedico.mapper;

import com.datos.umag.conultorioMedico.dto.AppointmentDTO;
import com.datos.umag.conultorioMedico.model.*;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO toDTO(Appointment entity);
    Appointment toEntity(AppointmentDTO dto);
}
