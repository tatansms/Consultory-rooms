package com.datos.umag.conultorioMedico.mapper;

import com.datos.umag.conultorioMedico.dto.PatientDTO;
import com.datos.umag.conultorioMedico.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDTO toDTO(Patient patient);

    Patient toEntity(PatientDTO patientDTO);

    void updateEntityFromDTO(PatientDTO patientDTO, @MappingTarget Patient patient);
}
