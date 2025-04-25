package com.datos.umag.conultorioMedico.mapper;

import com.datos.umag.conultorioMedico.dto.MedicalRecordDTO;
import com.datos.umag.conultorioMedico.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.fullName", target = "patientName")
    MedicalRecordDTO toDTO(MedicalRecord entity);

    @Mapping(target = "appointment.id", source = "appointmentId")
    @Mapping(target = "patient.id", source = "patientId")
    MedicalRecord toEntity(MedicalRecordDTO dto);
}
