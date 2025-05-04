package com.datos.umag.consultorioMedico.serviceImp;

import com.datos.umag.consultorioMedico.dto.request.PatientDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.PatientDtoResponse;
import com.datos.umag.consultorioMedico.exception.notFound.PatientNotFoundException;
import com.datos.umag.consultorioMedico.mapper.PatientMapper;
import com.datos.umag.consultorioMedico.model.Patient;
import com.datos.umag.consultorioMedico.repository.PatientRepository;
import com.datos.umag.consultorioMedico.service.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl service;

    private Patient patient;
    private PatientDtoRequest dtoRequest;
    private PatientDtoResponse dtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = Patient.builder()
                .idPatient(1L)
                .fullName("Test Name")
                .email("test@example.com")
                .phone("1234567")
                .build();
        dtoRequest = new PatientDtoRequest(
                "New Name", "new@example.com", "7654321"
        );
        dtoResponse = new PatientDtoResponse(
                1L,
                patient.getFullName(),
                patient.getEmail(),
                patient.getPhone()
        );
    }

    @Test
    void findAllPatients_returnsMappedList() {
        when(patientRepository.findAll()).thenReturn(List.of(patient));
        when(patientMapper.toPatientDtoResponse(patient)).thenReturn(dtoResponse);

        List<PatientDtoResponse> result = service.findAllPatients();

        assertThat(result).containsExactly(dtoResponse);
        verify(patientRepository).findAll();
        verify(patientMapper).toPatientDtoResponse(patient);
    }

    @Test
    void findPatientById_whenExists_returnsDto() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.toPatientDtoResponse(patient)).thenReturn(dtoResponse);

        PatientDtoResponse result = service.findPatientById(1L);

        assertThat(result).isEqualTo(dtoResponse);
    }

    @Test
    void findPatientById_whenNotFound_throwsException() {
        when(patientRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class,
                () -> service.findPatientById(2L));
    }

    @Test
    void savePatient_savesAndReturnsDto() {
        Patient entity = Patient.builder()
                .fullName(dtoRequest.fullName())
                .email(dtoRequest.email())
                .phone(dtoRequest.phone())
                .build();
        when(patientMapper.toEntity(dtoRequest)).thenReturn(entity);
        when(patientRepository.save(entity)).thenReturn(patient);
        when(patientMapper.toPatientDtoResponse(patient)).thenReturn(dtoResponse);

        PatientDtoResponse result = service.savePatient(dtoRequest);

        assertThat(result).isEqualTo(dtoResponse);
        verify(patientMapper).toEntity(dtoRequest);
        verify(patientRepository).save(entity);
        verify(patientMapper).toPatientDtoResponse(patient);
    }

    @Test
    void updatePatient_whenExists_updatesAndReturnsDto() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toPatientDtoResponse(patient)).thenReturn(dtoResponse);

        PatientDtoResponse result = service.updatePatient(1L, dtoRequest);

        assertThat(patient.getFullName()).isEqualTo(dtoRequest.fullName());
        assertThat(patient.getEmail()).isEqualTo(dtoRequest.email());
        assertThat(patient.getPhone()).isEqualTo(dtoRequest.phone());
        assertThat(result).isEqualTo(dtoResponse);
        verify(patientRepository).save(patient);
    }

    @Test
    void updatePatient_whenNotFound_throwsException() {
        when(patientRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class,
                () -> service.updatePatient(2L, dtoRequest));
    }

    @Test
    void deletePatient_whenNotExists_throwsException() {
        when(patientRepository.existsById(3L)).thenReturn(false);
        assertThrows(PatientNotFoundException.class,
                () -> service.deletePatient(3L));
    }

    @Test
    void deletePatient_whenExists_deletes() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        service.deletePatient(1L);

        verify(patientRepository).deleteById(1L);
    }
}
