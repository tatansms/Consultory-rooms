package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.PatientDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.PatientDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRooms_returnsListOfPatients() {
        PatientDtoResponse response = new PatientDtoResponse(1L, "Carlos Pérez", "carlos@test.com", "3001234567");
        when(patientService.findAllPatients()).thenReturn(List.of(response));

        ResponseEntity<List<PatientDtoResponse>> result = patientController.getAllRooms();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        assertEquals("Carlos Pérez", result.getBody().get(0).fullName());
        verify(patientService, times(1)).findAllPatients();
    }

    @Test
    void getPatientById_returnsPatient() {
        PatientDtoResponse response = new PatientDtoResponse(1L, "Laura Jiménez", "laura@test.com", "3011234567");
        when(patientService.findPatientById(1L)).thenReturn(response);

        ResponseEntity<PatientDtoResponse> result = patientController.getPatientById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Laura Jiménez", result.getBody().fullName());
        verify(patientService, times(1)).findPatientById(1L);
    }

    @Test
    void createPatient_returnsCreatedPatient() {
        PatientDtoRequest request = new PatientDtoRequest("Carlos Pérez", "carlos@test.com", "3001234567");
        PatientDtoResponse response = new PatientDtoResponse(1L, "Carlos Pérez", "carlos@test.com", "3001234567");

        when(patientService.savePatient(request)).thenReturn(response);

        ResponseEntity<PatientDtoResponse> result = patientController.createPatient(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Carlos Pérez", result.getBody().fullName());
        verify(patientService, times(1)).savePatient(request);
    }

    @Test
    void updatePatient_returnsUpdatedPatient() {
        PatientDtoRequest request = new PatientDtoRequest("Carlos Gómez", "carlos.gomez@test.com", "3021234567");
        PatientDtoResponse response = new PatientDtoResponse(1L, "Carlos Gómez", "carlos.gomez@test.com", "3021234567");

        when(patientService.updatePatient(1L, request)).thenReturn(response);

        ResponseEntity<PatientDtoResponse> result = patientController.updatePatient(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Carlos Gómez", result.getBody().fullName());
        verify(patientService, times(1)).updatePatient(1L, request);
    }

    @Test
    void deletePatient_returnsNoContent() {
        ResponseEntity<Void> result = patientController.deletePatient(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(patientService, times(1)).deletePatient(1L);
    }
}