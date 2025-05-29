package com.datos.umag.consultorioMedico.mapper;

import com.datos.umag.consultorioMedico.dto.request.MedicalRecordDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.MedicalRecordDtoResponse;
import com.datos.umag.consultorioMedico.model.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "patient.idPatient", target = "idPatient")
    @Mapping(source = "appointment.idAppointment", target = "idAppointment")
    MedicalRecordDtoResponse toMedicalRecordDtoResponse(MedicalRecord medicalRecord);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    MedicalRecord toEntity(MedicalRecordDtoRequest medicalRecord);

}