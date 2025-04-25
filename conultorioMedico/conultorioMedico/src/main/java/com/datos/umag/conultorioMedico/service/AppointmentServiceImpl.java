package com.datos.umag.conultorioMedico.service;

import com.datos.umag.conultorioMedico.dto.AppointmentDTO;
import com.datos.umag.conultorioMedico.mapper.AppointmentMapper;
import com.datos.umag.conultorioMedico.model.Appointment;
import com.datos.umag.conultorioMedico.model.Doctor;
import com.datos.umag.conultorioMedico.model.ConsultRoom;
import com.datos.umag.conultorioMedico.repository.AppointmentRepository;
import com.datos.umag.conultorioMedico.repository.DoctorRepository;
import com.datos.umag.conultorioMedico.repository.ConsultRoomRepository;
import com.datos.umag.conultorioMedico.service.IService.IAppointmentService;
import com.datos.umag.conultorioMedico.util.AppointmentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ConsultRoomRepository consultRoomRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentDTO> getAll() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cita no encontrada"));
        return appointmentMapper.toDTO(appointment);
    }

    @Override
    @Transactional
    public AppointmentDTO create(AppointmentDTO dto) {
        validateDate(dto.getStartTime(), dto.getEndTime());

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Doctor no encontrado"));
        ConsultRoom room = consultRoomRepository.findById(dto.getConsultRoomId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Consultorio no encontrado"));

        validateDoctorSchedule(doctor, dto.getStartTime().toLocalTime(), dto.getEndTime().toLocalTime());
        validateConflict(dto.getDoctorId(), dto.getConsultRoomId(), dto.getStartTime(), dto.getEndTime(), null);

        Appointment appointment = appointmentMapper.toEntity(dto);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Override
    @Transactional
    public AppointmentDTO update(Long id, AppointmentDTO dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cita no encontrada"));

        if (existing.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(BAD_REQUEST, "No se puede modificar una cita pasada");
        }

        validateDate(dto.getStartTime(), dto.getEndTime());
        validateDoctorSchedule(existing.getDoctor(), dto.getStartTime().toLocalTime(), dto.getEndTime().toLocalTime());
        validateConflict(dto.getDoctorId(), dto.getConsultRoomId(), dto.getStartTime(), dto.getEndTime(), id);

        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setStatus(parseStatus(String.valueOf(dto.getStatus())));

        return appointmentMapper.toDTO(appointmentRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Cita no encontrada");
        }
        appointmentRepository.deleteById(id);
    }


    private void validateDate(LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(BAD_REQUEST, "No se puede agendar una cita en el pasado");
        }
        if (!start.isBefore(end)) {
            throw new ResponseStatusException(BAD_REQUEST, "La hora de inicio debe ser antes que la de fin");
        }
    }

    private void validateDoctorSchedule(Doctor doctor, LocalTime start, LocalTime end) {
        if (start.isBefore(doctor.getAvailableFrom()) || end.isAfter(doctor.getAvailableTo())) {
            throw new ResponseStatusException(BAD_REQUEST, "La cita está fuera del horario del doctor");
        }
    }

    private void validateConflict(Long doctorId, Long roomId, LocalDateTime start, LocalDateTime end, Long excludeId) {
        boolean conflict = appointmentRepository.existsConflict(doctorId, roomId, start, end, excludeId);
        if (conflict) {
            throw new ResponseStatusException(CONFLICT, "Conflicto de horario con doctor o consultorio");
        }
    }

    private AppointmentStatus parseStatus(String status) {
        try {
            return AppointmentStatus.valueOf(status);
        } catch (Exception e) {
            throw new ResponseStatusException(BAD_REQUEST, "Estado de cita inválido: " + status);
        }
    }
}
