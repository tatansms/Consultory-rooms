package com.datos.umag.conultorioMedico.mock;


import com.datos.umag.conultorioMedico.controller.AppointmentController;
import com.datos.umag.conultorioMedico.dto.AppointmentDTO;
import com.datos.umag.conultorioMedico.service.IService.IAppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AppointmentController.class)
class AppointmentControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;


    private IAppointmentService appointmentService;

    @Test
    void testGetAllAppointments() throws Exception {
        when(appointmentService.getAll()).thenReturn(List.of(new AppointmentDTO()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreateAppointment() throws Exception {
        AppointmentDTO dto = new AppointmentDTO();
        String json = new ObjectMapper().writeValueAsString(dto);

        when(appointmentService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
