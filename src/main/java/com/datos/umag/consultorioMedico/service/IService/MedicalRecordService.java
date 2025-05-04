package com.datos.umag.consultorioMedico.service.IService;

import com.datos.umag.consultorioMedico.dto.request.MedicalRecordDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.MedicalRecordDtoResponse;

import java.util.List;


public interface MedicalRecordService {

    List<MedicalRecordDtoResponse> findAllMedicalRecords();
    MedicalRecordDtoResponse findById(Long id);
    List<MedicalRecordDtoResponse> findMedicalRecordsByPatient(Long id);
    MedicalRecordDtoResponse saveMedicalRecord(MedicalRecordDtoRequest medicalRecordDtoRequest);
    void deleteMedicalRecord(Long id);
}