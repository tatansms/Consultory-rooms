package com.datos.umag.consultorioMedico.service;

import com.datos.umag.consultorioMedico.dto.request.MedicalRecordDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.MedicalRecordDtoResponse;
import com.datos.umag.consultorioMedico.exception.AppointmentCanceledException;
import com.datos.umag.consultorioMedico.exception.AppointmentStillScheduledException;
import com.datos.umag.consultorioMedico.exception.notFound.AppointmentNotFoundException;
import com.datos.umag.consultorioMedico.exception.notFound.MedicalRecordNotFoundException;
import com.datos.umag.consultorioMedico.exception.notFound.PatientNotFoundException;
import com.datos.umag.consultorioMedico.mapper.MedicalRecordMapper;
import com.datos.umag.consultorioMedico.model.Appointment;
import com.datos.umag.consultorioMedico.model.MedicalRecord;
import com.datos.umag.consultorioMedico.model.Patient;
import com.datos.umag.consultorioMedico.repository.AppointmentRepository;
import com.datos.umag.consultorioMedico.repository.ConsultRoomRepository;
import com.datos.umag.consultorioMedico.repository.MedicalRecordRepository;
import com.datos.umag.consultorioMedico.repository.PatientRepository;
import com.datos.umag.consultorioMedico.service.IService.MedicalRecordService;
import com.datos.umag.consultorioMedico.util.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final ConsultRoomRepository consultRoomRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;
    private MedicalRecordMapper medicalRecordMapper;


    @Override
    public List<MedicalRecordDtoResponse> findAllMedicalRecords() {
        return medicalRecordRepository.findAll().stream()
                .map(medicalRecordMapper::toMedicalRecordDtoResponse)
                .toList();
    }

    @Override
    public MedicalRecordDtoResponse findById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical Record with ID: " + id + " Not Found"));

        return medicalRecordMapper.toMedicalRecordDtoResponse(medicalRecord);

    }

    @Override
    public List<MedicalRecordDtoResponse> findMedicalRecordsByPatient(Long id) {

        if(!patientRepository.existsById(id)){
            throw new PatientNotFoundException("Patient with ID: " + id + " Not Found");
        }

        List<MedicalRecordDtoResponse> result = medicalRecordRepository.findByPatientId(id).stream()
                .map(medicalRecordMapper::toMedicalRecordDtoResponse)
                .toList();

        return result;
    }

    @Override
    public MedicalRecordDtoResponse saveMedicalRecord(MedicalRecordDtoRequest medicalRecordDtoRequest) {

        Patient patient = patientRepository.findById(medicalRecordDtoRequest.idPatient())
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID: " + medicalRecordDtoRequest.idPatient() + " Not Found"));

        Appointment appointment = appointmentRepository.findById(medicalRecordDtoRequest.idAppointment())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID: " + medicalRecordDtoRequest.idAppointment() + " Not Found"));

        if(appointment.getStatus() == AppointmentStatus.SCHEDULED){
            throw new AppointmentStillScheduledException("Appointment's status is Scheduled");
        }

        if(appointment.getStatus() == AppointmentStatus.CANCELED){
            throw new AppointmentCanceledException("Appointment has been canceled");
        }

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(medicalRecordDtoRequest);
        medicalRecord.setPatient(patient);
        medicalRecord.setAppointment(appointment);

        MedicalRecord savedEntity = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toMedicalRecordDtoResponse(savedEntity);

    }

    @Override
    public void deleteMedicalRecord(Long id) {

        if(!medicalRecordRepository.existsById(id)){
            throw new MedicalRecordNotFoundException("Medical Record with ID: " + id + " Not Found");
        }

        medicalRecordRepository.deleteById(id);

    }
}