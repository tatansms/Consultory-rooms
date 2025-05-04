package com.datos.umag.consultorioMedico.service.IService;

import com.datos.umag.consultorioMedico.dto.request.DoctorDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.DoctorDtoResponse;

import java.util.List;

public interface DoctorService {

    List<DoctorDtoResponse> findAllDoctors();
    DoctorDtoResponse findDoctorById(Long idDoctor);
    DoctorDtoResponse saveDoctor(DoctorDtoRequest doctorDtoRequest);
    DoctorDtoResponse updateDoctor(Long idDoctor, DoctorDtoRequest doctorDtoRequest);
    void deleteDoctor(Long idDoctor);

}