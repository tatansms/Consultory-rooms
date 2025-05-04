package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.DoctorDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.DoctorDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;
    @InjectMocks
    private DoctorController doctorController;

    private DoctorDtoResponse sampleResponse;
    private DoctorDtoRequest sampleRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleResponse = new DoctorDtoResponse(
                1L,
                "Dr. Jane Doe",
                "jane.doe@clinic.com",
                "Pediatría",
                LocalTime.of(8,30),
                LocalTime.of(15,0)
        );
        sampleRequest = new DoctorDtoRequest(
                "Dr. Jane Doe",
                "jane.doe@clinic.com",
                "Pediatría",
                LocalTime.of(8,30),
                LocalTime.of(15,0)
        );
    }

    @Test
    void getAllDoctors_returnsOkAndList() {
        when(doctorService.findAllDoctors()).thenReturn(List.of(sampleResponse));

        ResponseEntity<List<DoctorDtoResponse>> response = doctorController.getAllDoctors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleResponse, response.getBody().get(0));
        verify(doctorService).findAllDoctors();
    }

    @Test
    void getDoctorById_returnsOkAndDto() {
        when(doctorService.findDoctorById(1L)).thenReturn(sampleResponse);

        ResponseEntity<DoctorDtoResponse> response = doctorController.getDoctorById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(doctorService).findDoctorById(1L);
    }

    @Test
    void createDoctor_returnsCreatedAndDto() {
        when(doctorService.saveDoctor(any(DoctorDtoRequest.class))).thenReturn(sampleResponse);

        ResponseEntity<DoctorDtoResponse> response = doctorController.createDoctor(sampleRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(doctorService).saveDoctor(sampleRequest);
    }

    @Test
    void updateDoctor_returnsOkAndDto() {
        when(doctorService.updateDoctor(eq(1L), any(DoctorDtoRequest.class))).thenReturn(sampleResponse);

        ResponseEntity<DoctorDtoResponse> response = doctorController.updateDoctor(1L, sampleRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(doctorService).updateDoctor(1L, sampleRequest);
    }

    @Test
    void deleteDoctor_returnsNoContent() {
        doNothing().when(doctorService).deleteDoctor(1L);

        ResponseEntity<Void> response = doctorController.deleteDoctor(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(doctorService).deleteDoctor(1L);
    }
}