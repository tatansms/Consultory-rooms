package com.datos.umag.consultorioMedico.mapper;

import com.datos.umag.consultorioMedico.dto.request.AppointmentDtoRequest;
import com.datos.umag.consultorioMedico.dto.request.AppointmentDtoUpdateRequest;
import com.datos.umag.consultorioMedico.dto.response.AppointmentDtoResponse;
import com.datos.umag.consultorioMedico.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source = "patient.idPatient", target = "idPatient")
    @Mapping(source = "doctor.idDoctor", target = "idDoctor")
    @Mapping(source = "consultRoom.idConsultRoom", target = "idConsultRoom")
    AppointmentDtoResponse toAppointmentDtoResponse(Appointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "consultRoom", ignore = true)
    Appointment toEntity(AppointmentDtoRequest appointment);

    void updateAppointmentFromDto(AppointmentDtoUpdateRequest dto, @MappingTarget Appointment appointment);

}