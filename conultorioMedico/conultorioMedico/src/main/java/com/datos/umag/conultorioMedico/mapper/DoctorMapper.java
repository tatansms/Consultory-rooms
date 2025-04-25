package com.datos.umag.conultorioMedico.mapper;



import com.datos.umag.conultorioMedico.model.Doctor;
import com.datos.umag.conultorioMedico.dto.DoctorDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDto(Doctor doctor);

    Doctor toEntity(DoctorDTO doctorDTO);

    List<DoctorDTO> toDtoList(List<Doctor> doctors);

    List<Doctor> toEntityList(List<DoctorDTO> dtos);
}
