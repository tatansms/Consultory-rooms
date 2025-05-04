package com.datos.umag.consultorioMedico.service;

import com.datos.umag.consultorioMedico.dto.request.PatientDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.PatientDtoResponse;
import com.datos.umag.consultorioMedico.exception.notFound.PatientNotFoundException;
import com.datos.umag.consultorioMedico.mapper.PatientMapper;
import com.datos.umag.consultorioMedico.model.Patient;
import com.datos.umag.consultorioMedico.repository.PatientRepository;
import com.datos.umag.consultorioMedico.service.IService.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<PatientDtoResponse> findAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toPatientDtoResponse)
                .toList();
    }

    @Override
    public PatientDtoResponse findPatientById(Long idPatient) {
        return patientRepository.findById(idPatient)
                .map(patientMapper::toPatientDtoResponse)
                .orElseThrow(() -> new PatientNotFoundException("Patient wiht ID: " + idPatient + " not found"));
    }

    @Override
    public PatientDtoResponse savePatient(PatientDtoRequest patient) {
        Patient patientMapped = patientMapper.toEntity(patient);
        return patientMapper.toPatientDtoResponse(patientRepository.save(patientMapped));
    }

    @Override
    public PatientDtoResponse updatePatient(Long idPatient, PatientDtoRequest patient) {
        Patient patient1 = patientRepository.findById(idPatient)
                .orElseThrow(() -> new PatientNotFoundException("Patient wiht ID: " + idPatient + " not found"));

        patient1.setFullName(patient.fullName());
        patient1.setEmail(patient.email());
        patient1.setPhone(patient.phone());

        return patientMapper.toPatientDtoResponse(patientRepository.save(patient1));
    }

    @Override
    public void deletePatient(Long idPatient) {

        if(!patientRepository.existsById(idPatient)){
            throw new PatientNotFoundException("Patient with ID: " + idPatient + " not found");
        }

        patientRepository.deleteById(idPatient);

    }
}