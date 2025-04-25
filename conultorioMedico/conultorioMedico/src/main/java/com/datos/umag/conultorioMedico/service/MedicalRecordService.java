package com.datos.umag.conultorioMedico.service;

import com.datos.umag.conultorioMedico.dto.MedicalRecordDTO;
import com.datos.umag.conultorioMedico.mapper.MedicalRecordMapper;
import com.datos.umag.conultorioMedico.model.MedicalRecord;
import com.datos.umag.conultorioMedico.repository.MedicalRecordRepository;
import com.datos.umag.conultorioMedico.service.IService.IMedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, MedicalRecordMapper medicalRecordMapper) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordMapper = medicalRecordMapper;
    }

    @Override
    public List<MedicalRecordDTO> getAll() {
        List<MedicalRecord> records = medicalRecordRepository.findAll();
        return records.stream()
                .map(medicalRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalRecordDTO getById(Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found"));
        return medicalRecordMapper.toDTO(record);
    }

    @Override
    public List<MedicalRecordDTO> getByPatientId(Long patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);
        return records.stream()
                .map(medicalRecordMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalRecordDTO create(MedicalRecordDTO dto) {
        MedicalRecord record = medicalRecordMapper.toEntity(dto);
        MedicalRecord saved = medicalRecordRepository.save(record);
        return medicalRecordMapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new RuntimeException("Medical record not found");
        }
        medicalRecordRepository.deleteById(id);
    }
}
