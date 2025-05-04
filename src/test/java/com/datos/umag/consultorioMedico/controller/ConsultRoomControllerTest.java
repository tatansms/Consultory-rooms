package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.ConsultRoomDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.ConsultRoomDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.ConsultRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConsultRoomControllerTest {

    @Mock
    private ConsultRoomService consultRoomService;
    @InjectMocks
    private ConsultRoomController consultRoomController;

    private ConsultRoomDtoResponse sampleResponse;
    private ConsultRoomDtoRequest sampleRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleResponse = new ConsultRoomDtoResponse(5L, "Sala A", "2", "Sala grande");
        sampleRequest = new ConsultRoomDtoRequest("Sala B", "3", "Sala pequeña");
    }

    @Test
    void getAllConsultRooms_returnsOkAndList() {
        when(consultRoomService.findAllConsultRooms()).thenReturn(List.of(sampleResponse));

        ResponseEntity<List<ConsultRoomDtoResponse>> response = consultRoomController.getAllConsultRooms();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleResponse, response.getBody().get(0));
        verify(consultRoomService).findAllConsultRooms();
    }

    @Test
    void getConsultRoomById_returnsOkAndDto() {
        when(consultRoomService.findConsultRoomById(5L)).thenReturn(sampleResponse);

        ResponseEntity<ConsultRoomDtoResponse> response = consultRoomController.getConsultRoomById(5L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(consultRoomService).findConsultRoomById(5L);
    }

    @Test
    void createConsultRoom_returnsCreatedAndDto() {
        when(consultRoomService.saveConsultRoom(any(ConsultRoomDtoRequest.class))).thenReturn(sampleResponse);

        ResponseEntity<ConsultRoomDtoResponse> response = consultRoomController.createConsultRoom(sampleRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(consultRoomService).saveConsultRoom(sampleRequest);
    }

    @Test
    void updateConsultRoom_returnsOkAndDto() {
        when(consultRoomService.updateConsultRoom(eq(5L), any(ConsultRoomDtoRequest.class))).thenReturn(sampleResponse);

        ResponseEntity<ConsultRoomDtoResponse> response = consultRoomController.updateConsultRoom(5L, sampleRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleResponse, response.getBody());
        verify(consultRoomService).updateConsultRoom(5L, sampleRequest);
    }

    @Test
    void deleteConsultRoom_returnsNoContent() {
        doNothing().when(consultRoomService).deleteConsultRoom(5L);

        ResponseEntity<Void> response = consultRoomController.deleteConsultRoom(5L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(consultRoomService).deleteConsultRoom(5L);
    }
}