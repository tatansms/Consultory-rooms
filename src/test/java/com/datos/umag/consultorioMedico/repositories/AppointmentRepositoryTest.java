package com.datos.umag.consultorioMedico.repositories;

import com.datos.umag.consultorioMedico.model.*;
import com.datos.umag.consultorioMedico.repository.*;
import com.datos.umag.consultorioMedico.util.AppointmentStatus;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class AppointmentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ConsultRoomRepository consultRoomRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private Patient paciente;
    private Doctor doctor;
    private ConsultRoom consultorio;
    private MedicalRecord medicalRecord;
    private Appointment appointment;

    LocalDateTime start;
    LocalDateTime diagnostico;
    LocalDateTime end;

    @BeforeEach
    void setUp() {
        start = LocalDateTime.now().plusDays(1).withHour(9);
        diagnostico = LocalDateTime.now().plusDays(1).withHour(9);
        end = start.plusHours(1);

        paciente = patientRepository.save(
                Patient.builder()
                        .fullName("Paciente Test")
                        .email("paciente@test.com")
                        .phone("3001234567")
                        .build()
        );

        doctor = doctorRepository.save(
                Doctor.builder()
                        .fullName("Dr. House")
                        .email("house@princeton.com")
                        .speciality("Diagnóstico")
                        .availableFrom(LocalTime.of(8, 0))
                        .availableTo(LocalTime.of(16, 0))
                        .build()
        );

        consultorio = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consultorio 1")
                        .floor("3")
                        .description("Sala de diagnóstico")
                        .build()
        );

        appointment = Appointment.builder()
                .patient(paciente)
                .consultRoom(consultorio)
                .doctor(doctor)
                .startTime(start)
                .endTime(end)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        medicalRecord = MedicalRecord.builder()
                .patient(paciente)
                .appointment(appointment)
                .notes("Alergico al acetaminofen")
                .diagnosis("gripe")
                .createdAt(diagnostico)
                .build();

        appointment.setMedicalRecord(medicalRecord);

        appointmentRepository.save(appointment);
    }

    @AfterEach
    void tearDown() {
        medicalRecordRepository.deleteAll();
        appointmentRepository.deleteAll();
        consultRoomRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void shouldDetectConflictingAppointment() {
        // Cita existente va de 'start' a 'end'
        LocalDateTime conflictStart = start.plusMinutes(30);
        LocalDateTime conflictEnd = end.plusMinutes(30);

        List<Appointment> conflicts = appointmentRepository.findConflicts(
                appointment.getConsultRoom().getIdConsultRoom(),
                conflictStart,
                conflictEnd
        );

        assertFalse(conflicts.isEmpty(), "Debe detectar conflicto en el mismo consultorio");
    }

    @Test
    void shouldDetectConflictingDoctor() {
        // Creamos un segundo consultorio para aislar conflicto de consultorio
        ConsultRoom otroConsultorio = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consultorio 2")
                        .floor("4")
                        .description("Sala auxiliar")
                        .build()
        );
        LocalDateTime conflictStart = start.plusMinutes(30);
        LocalDateTime conflictEnd = end.plusMinutes(30);

        List<Appointment> conflicts = appointmentRepository.findConflicsWithDoctor(
                appointment.getDoctor().getIdDoctor(),
                conflictStart,
                conflictEnd
        );
        assertFalse(conflicts.isEmpty(), "Debe detectar conflicto con el mismo doctor");
    }

    @Test
    void shouldSaveAppointmentWithoutConflict(){
        LocalDateTime newStart = end.plusHours(1); // Hora posterior a la cita existente
        LocalDateTime newEnd = newStart.plusHours(1);

        // Crear nuevo registro médico
        MedicalRecord newMedicalRecord = MedicalRecord.builder()
                .patient(paciente)
                .notes("Consulta de control")
                .diagnosis("Salud estable")
                .createdAt(LocalDateTime.now())
                .build();

        // Construir nueva cita sin solapamientos
        Appointment newAppointment = Appointment.builder()
                .patient(paciente)
                .consultRoom(consultorio)
                .doctor(doctor)
                .startTime(newStart)
                .endTime(newEnd)
                .medicalRecord(newMedicalRecord)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        // Relación bidireccional
        newMedicalRecord.setAppointment(newAppointment);

        // Guardar y verificar
        Appointment savedAppointment = appointmentRepository.save(newAppointment);

        assertNotNull(savedAppointment.getIdAppointment());
        assertEquals(newStart, savedAppointment.getStartTime());
        assertEquals(newEnd, savedAppointment.getEndTime());
        assertEquals(consultorio.getIdConsultRoom(), savedAppointment.getConsultRoom().getIdConsultRoom());
        assertNotNull(savedAppointment.getMedicalRecord().getIdMedicalRecord());
    }

    @Test
    void shouldFindAppointmentById() {
        Optional<Appointment> foundAppointment = appointmentRepository.findById(appointment.getIdAppointment());
        assertTrue(foundAppointment.isPresent(), "Debe encontrar la cita por ID");
        assertEquals(appointment.getStartTime(), foundAppointment.get().getStartTime());
    }

    @Test
    void shouldDeleteAppointment() {
        Long appointmentId = appointment.getIdAppointment();
        appointmentRepository.delete(appointment);

        Optional<Appointment> deletedAppointment = appointmentRepository.findById(appointmentId);
        Optional<MedicalRecord> deletedMedicalRecord = medicalRecordRepository.findById(medicalRecord.getIdMedicalRecord());

        assertTrue(deletedAppointment.isEmpty(), "La cita debe eliminarse");
        assertTrue(deletedMedicalRecord.isEmpty(), "El registro médico asociado también debe eliminarse");
    }



}
