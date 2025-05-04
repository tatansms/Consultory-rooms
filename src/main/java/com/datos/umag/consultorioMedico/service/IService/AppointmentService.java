package com.datos.umag.consultorioMedico.service.IService;

import com.datos.umag.consultorioMedico.dto.request.AppointmentDtoRequest;
import com.datos.umag.consultorioMedico.dto.request.AppointmentDtoUpdateRequest;
import com.datos.umag.consultorioMedico.dto.response.AppointmentDtoResponse;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDtoResponse> findAllAppointments();
    AppointmentDtoResponse findAppointmentById(Long id);
    AppointmentDtoResponse saveAppointment(AppointmentDtoRequest appointmentDtoRequest);
    AppointmentDtoResponse updateAppointment(Long id, AppointmentDtoUpdateRequest appointmentDtoRequest);
    void deleteAppointment(Long id);

}