package com.datos.umag.consultorioMedico.mapper;


import com.datos.umag.consultorioMedico.dto.request.DoctorDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.DoctorDtoResponse;
import com.datos.umag.consultorioMedico.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDtoResponse toDoctorDtoResponse(Doctor doctor);

    @Mapping(target = "idDoctor", ignore = true)
    Doctor toEntity(DoctorDtoRequest doctorDto);


}