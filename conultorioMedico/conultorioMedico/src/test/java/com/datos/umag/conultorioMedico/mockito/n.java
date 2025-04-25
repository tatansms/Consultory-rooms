package com.datos.umag.conultorioMedico.mockito;

import com.datos.umag.conultorioMedico.controller.AppointmentController;
import com.datos.umag.conultorioMedico.dto.AppointmentDTO;
import com.datos.umag.conultorioMedico.service.IService.IAppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private IAppointmentService appointmentService;

    @InjectMocks
    private AppointmentController controller;

    @Test
    void testGetAllAppointments() {
        List<AppointmentDTO> mockList = List.of(new AppointmentDTO());
        when(appointmentService.getAll()).thenReturn(mockList);

        ResponseEntity<List<AppointmentDTO>> response = controller.getAllAppointments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void testGetAppointmentById() {
        AppointmentDTO dto = new AppointmentDTO();
        when(appointmentService.getById(1L)).thenReturn(dto);

        ResponseEntity<AppointmentDTO> response = controller.getAppointmentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testCreateAppointment() {
        AppointmentDTO input = new AppointmentDTO();
        AppointmentDTO saved = new AppointmentDTO();
        when(appointmentService.create(input)).thenReturn(saved);

        ResponseEntity<AppointmentDTO> response = controller.createAppointment(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved, response.getBody());
    }

    @Test
    void testUpdateAppointment() {
        AppointmentDTO input = new AppointmentDTO();
        AppointmentDTO updated = new AppointmentDTO();
        when(appointmentService.update(1L, input)).thenReturn(updated);

        ResponseEntity<AppointmentDTO> response = controller.updateAppointment(1L, input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testDeleteAppointment() {
        doNothing().when(appointmentService).delete(1L);

        ResponseEntity<Void> response = controller.cancelAppointment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
