package com.datos.umag.consultorioMedico.service.IService;

import com.datos.umag.consultorioMedico.dto.request.PatientDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.PatientDtoResponse;

import java.util.List;

public interface PatientService {
    List<PatientDtoResponse> findAllPatients();
    PatientDtoResponse findPatientById(Long idPatient);
    PatientDtoResponse savePatient(PatientDtoRequest patient);
    PatientDtoResponse updatePatient(Long idPatient, PatientDtoRequest patient);
    void deletePatient(Long idPatient);

}

