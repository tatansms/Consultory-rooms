package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.MedicalRecordDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.MedicalRecordDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MedicalRecordControllerTest {

    @Mock private MedicalRecordService medicalRecordService;
    @InjectMocks private MedicalRecordController medicalRecordController;

    private MedicalRecordDtoResponse sampleResponse;
    private MedicalRecordDtoRequest sampleRequest;
    private LocalDateTime createdAt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createdAt = LocalDateTime.of(2025, 5, 10, 10, 0);
        sampleResponse = new MedicalRecordDtoResponse(
                1L, 2L, 3L, "Dx", "Notes", createdAt
        );
        sampleRequest = new MedicalRecordDtoRequest(
                2L, 3L, "Dx", "Notes", createdAt
        );
    }

    @Test
    void getAllMedicalRecords_returnsOkAndList() {
        when(medicalRecordService.findAllMedicalRecords()).thenReturn(List.of(sampleResponse));

        ResponseEntity<List<MedicalRecordDtoResponse>> response = medicalRecordController.getAllMedicalRecords();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleResponse, response.getBody().get(0));
        verify(medicalRecordService).findAllMedicalRecords();
    }

    @Test
    void getMedicalRecordById_returnsOkAndDto() {
        when(medicalRecordService.findById(1L)).thenReturn(sampleResponse);

        ResponseEntity<MedicalRecordDtoResponse> response = medicalRecordController.getMedicalRecordById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(medicalRecordService).findById(1L);
    }

    @Test
    void getMedicalRecordsByPatientId_returnsOkAndList() {
        when(medicalRecordService.findMedicalRecordsByPatient(2L)).thenReturn(List.of(sampleResponse));

        ResponseEntity<List<MedicalRecordDtoResponse>> response = medicalRecordController.getMedicalRecordsByPatientId(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleResponse, response.getBody().get(0));
        verify(medicalRecordService).findMedicalRecordsByPatient(2L);
    }

    @Test
    void createMedicalRecord_returnsCreatedAndDto() {
        when(medicalRecordService.saveMedicalRecord(any(MedicalRecordDtoRequest.class))).thenReturn(sampleResponse);

        ResponseEntity<MedicalRecordDtoResponse> response = medicalRecordController.createMedicalRecord(sampleRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(medicalRecordService).saveMedicalRecord(sampleRequest);
    }

    @Test
    void deleteMedicalRecord_returnsNoContent() {
        doNothing().when(medicalRecordService).deleteMedicalRecord(1L);

        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(medicalRecordService).deleteMedicalRecord(1L);
    }
}