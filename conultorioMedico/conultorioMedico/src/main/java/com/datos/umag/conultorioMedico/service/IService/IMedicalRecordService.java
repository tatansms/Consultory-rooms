package com.datos.umag.conultorioMedico.service.IService;

import com.datos.umag.conultorioMedico.dto.MedicalRecordDTO;

import java.util.List;

public interface IMedicalRecordService {
    List<MedicalRecordDTO> getAll();
    MedicalRecordDTO getById(Long id);
    List<MedicalRecordDTO> getByPatientId(Long patientId);
    MedicalRecordDTO create(MedicalRecordDTO dto);
    void delete(Long id);
}
