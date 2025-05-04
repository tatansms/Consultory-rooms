package com.datos.umag.consultorioMedico.mapper;

import com.datos.umag.consultorioMedico.dto.request.PatientDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.PatientDtoResponse;
import com.datos.umag.consultorioMedico.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {


    PatientDtoResponse toPatientDtoResponse(Patient patient);
    Patient toEntity(PatientDtoRequest patient);
}