package com.datos.umag.conultorioMedico.service.IService;

import com.datos.umag.conultorioMedico.dto.PatientDTO;

import java.util.List;

public interface IPatientService {
    PatientDTO getPatientById(Long id);
    List<PatientDTO> getAllPatients();
    PatientDTO createPatient(PatientDTO patientDTO);
    PatientDTO updatePatient(Long id, PatientDTO patientDTO);
    void deletePatient(Long id);
}

