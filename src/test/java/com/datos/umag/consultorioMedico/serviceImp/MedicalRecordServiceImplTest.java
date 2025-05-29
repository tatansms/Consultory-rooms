package com.datos.umag.consultorioMedico.serviceImp;

import com.datos.umag.consultorioMedico.dto.request.MedicalRecordDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.MedicalRecordDtoResponse;
import com.datos.umag.consultorioMedico.exception.AppointmentStillScheduledException;
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
import com.datos.umag.consultorioMedico.service.MedicalRecordServiceImpl;
import com.datos.umag.consultorioMedico.util.AppointmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ConsultRoomRepository consultRoomRepository;
    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @InjectMocks
    private MedicalRecordServiceImpl service;

    private Patient patient;
    private Appointment appointment;
    private MedicalRecordDtoRequest dtoRequest;
    private MedicalRecordDtoResponse dtoResponse;
    private LocalDateTime createdAt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = Patient.builder().idPatient(1L).build();
        appointment = Appointment.builder()
                .idAppointment(2L)
                .status(AppointmentStatus.COMPLETED)
                .build();
        createdAt = LocalDateTime.of(2025,5,1,10,0);
        dtoRequest = new MedicalRecordDtoRequest(1L,2L,
                "Dx","Notes",createdAt);
        dtoResponse = new MedicalRecordDtoResponse(
                3L,1L,2L,"Dx","Notes",createdAt
        );
    }

    @Test
    void findAllMedicalRecords_returnsMappedList() {
        MedicalRecord rec = MedicalRecord.builder().idMedicalRecord(4L).build();
        when(medicalRecordRepository.findAll()).thenReturn(List.of(rec));
        when(medicalRecordMapper.toMedicalRecordDtoResponse(rec)).thenReturn(dtoResponse);

        List<MedicalRecordDtoResponse> result = service.findAllMedicalRecords();

        assertThat(result).containsExactly(dtoResponse);
        verify(medicalRecordRepository).findAll();
        verify(medicalRecordMapper).toMedicalRecordDtoResponse(rec);
    }

    @Test
    void findById_whenExists_returnsDto() {
        MedicalRecord rec = MedicalRecord.builder().idMedicalRecord(5L).build();
        when(medicalRecordRepository.findById(5L)).thenReturn(Optional.of(rec));
        when(medicalRecordMapper.toMedicalRecordDtoResponse(rec)).thenReturn(dtoResponse);

        MedicalRecordDtoResponse result = service.findById(5L);

        assertThat(result).isEqualTo(dtoResponse);
    }

    @Test
    void findById_whenNotFound_throwsException() {
        when(medicalRecordRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(MedicalRecordNotFoundException.class, () -> service.findById(9L));
    }

    @Test
    void findMedicalRecordsByPatient_whenPatientExists_returnsList() {
        MedicalRecord rec = MedicalRecord.builder().idMedicalRecord(6L).build();
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(medicalRecordRepository.findByPatient_IdPatient(1L)).thenReturn(List.of(rec));
        when(medicalRecordMapper.toMedicalRecordDtoResponse(rec)).thenReturn(dtoResponse);

        List<MedicalRecordDtoResponse> result = service.findMedicalRecordsByPatient(1L);

        assertThat(result).containsExactly(dtoResponse);
    }

    @Test
    void findMedicalRecordsByPatient_whenPatientNotFound_throwsException() {
        when(patientRepository.existsById(7L)).thenReturn(false);
        assertThrows(PatientNotFoundException.class,
                () -> service.findMedicalRecordsByPatient(7L));
    }

    @Test
    void saveMedicalRecord_whenAppointmentStillScheduled_throwsException() {
        Appointment sched = Appointment.builder()
                .idAppointment(8L)
                .status(AppointmentStatus.SCHEDULED)
                .build();
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById(2L)).thenReturn(Optional.of(sched));

        assertThrows(AppointmentStillScheduledException.class,
                () -> service.saveMedicalRecord(dtoRequest));
    }

    @Test
    void saveMedicalRecord_whenValid_returnsDto() {
        // 1. Configurar datos de prueba
        MedicalRecord entity = MedicalRecord.builder()
                .diagnosis("Dx")
                .notes("Notes")
                .createdAt(createdAt)
                .build();

        // 2. Configurar mocks
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById(2L)).thenReturn(Optional.of(appointment));
        when(medicalRecordMapper.toEntity(dtoRequest)).thenReturn(entity);
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(entity);
        when(medicalRecordMapper.toMedicalRecordDtoResponse(entity)).thenReturn(dtoResponse);

        // 3. Ejecutar el método a probar
        MedicalRecordDtoResponse result = service.saveMedicalRecord(dtoRequest);

        // 4. Verificar el resultado
        assertThat(result).isEqualTo(dtoResponse);

        // 5. Verificar interacciones con los mocks
        ArgumentCaptor<MedicalRecord> captor = ArgumentCaptor.forClass(MedicalRecord.class);
        verify(medicalRecordRepository, times(1)).save(captor.capture());

        MedicalRecord savedEntity = captor.getValue();
        assertThat(savedEntity.getPatient()).isEqualTo(patient);
        assertThat(savedEntity.getAppointment()).isEqualTo(appointment);
    }

    @Test
    void deleteMedicalRecord_whenNotExists_throwsException() {
        when(medicalRecordRepository.existsById(10L)).thenReturn(false);
        assertThrows(MedicalRecordNotFoundException.class,
                () -> service.deleteMedicalRecord(10L));
    }

    @Test
    void deleteMedicalRecord_whenExists_deletes() {
        when(medicalRecordRepository.existsById(3L)).thenReturn(true);

        service.deleteMedicalRecord(3L);

        verify(medicalRecordRepository).deleteById(3L);
    }
}