package com.datos.umag.consultorioMedico.controller;
import com.datos.umag.consultorioMedico.dto.request.AppointmentDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.AppointmentDtoResponse;
import com.datos.umag.consultorioMedico.dto.request.AppointmentDtoUpdateRequest;
import com.datos.umag.consultorioMedico.service.IService.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDtoResponse>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDtoResponse> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.findAppointmentById(id));
    }

    @PostMapping
    public ResponseEntity<AppointmentDtoResponse> createAppointment(@Valid @RequestBody AppointmentDtoRequest appointmentDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.saveAppointment(appointmentDtoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDtoResponse> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentDtoUpdateRequest appointmentDtoRequest){
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

}