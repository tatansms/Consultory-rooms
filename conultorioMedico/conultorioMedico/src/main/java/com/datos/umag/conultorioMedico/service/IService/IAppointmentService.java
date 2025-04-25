package com.datos.umag.conultorioMedico.service.IService;

import com.datos.umag.conultorioMedico.dto.AppointmentDTO;

import java.util.List;

public interface IAppointmentService {
    List<AppointmentDTO> getAll();
    AppointmentDTO getById(Long id);
    AppointmentDTO create(AppointmentDTO dto);
    AppointmentDTO update(Long id, AppointmentDTO dto);
    void delete(Long id);
}
