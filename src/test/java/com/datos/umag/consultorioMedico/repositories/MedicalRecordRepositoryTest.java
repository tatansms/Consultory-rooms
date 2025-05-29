package com.datos.umag.consultorioMedico.repositories;

import com.datos.umag.consultorioMedico.model.*;
import com.datos.umag.consultorioMedico.repository.*;
import com.datos.umag.consultorioMedico.util.AppointmentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class MedicalRecordRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    @Test
    void shouldSaveMedicalRecordAndFind() {

        Patient patient = patientRepository.save(
                Patient.builder()
                        .fullName("Juan Pérez")
                        .email("juan.perez@example.com")
                        .phone("+573001234567")
                        .build());


        // Crear Doctor
        Doctor doctor = doctorRepository.save(
                Doctor.builder()
                        .fullName("Dra. María González")
                        .email("maria.gonzalez@clinica.com")
                        .speciality("Cardiología")
                        .availableFrom(LocalTime.of(8, 0))
                        .availableTo(LocalTime.of(17, 0))
                        .build());

        // Crear ConsultRoom
        ConsultRoom consultRoom = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consulta 101")
                        .floor("1")
                        .description("Consultorio de Cardiología")
                        .build());

        // Crear Appointment
        Appointment appointment = appointmentRepository.save(
                Appointment.builder()
                        .patient(patient)
                        .doctor(doctor)
                        .consultRoom(consultRoom)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30))
                        .status(AppointmentStatus.SCHEDULED)
                        .build());

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Enfermo")
                .notes("Sintomas leves")
                .createdAt(LocalDateTime.of(2025, 4, 18, 14, 0))
                .build();

        MedicalRecord saved = medicalRecordRepository.save(medicalRecord);

        Optional<MedicalRecord> result = medicalRecordRepository.findById(saved.getIdMedicalRecord());

        assertTrue(result.isPresent());
        assertEquals("Juan Pérez", result.get().getPatient().getFullName());
    }

    @Test
    void shouldFindAllMedicalRecords(){
        Patient patient = patientRepository.save(
                Patient.builder()
                        .fullName("Juan Pérez")
                        .email("juan.perez@example.com")
                        .phone("+573001234567")
                        .build());

        Patient patient1 = patientRepository.save(
                Patient.builder()
                        .fullName("Pedro Rodriguez")
                        .email("pedro.rodriguez@example.com")
                        .phone("+573014567890")
                        .build());


        // Crear Doctor
        Doctor doctor = doctorRepository.save(
                Doctor.builder()
                        .fullName("María González")
                        .email("maria.gonzalez@clinica.com")
                        .speciality("Cardiología")
                        .availableFrom(LocalTime.of(8, 0))
                        .availableTo(LocalTime.of(17, 0))
                        .build());

        // Crear ConsultRoom
        ConsultRoom consultRoom = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consulta 101")
                        .floor("1")
                        .description("Consultorio de Cardiología")
                        .build());

        // Crear Appointment
        Appointment appointment = appointmentRepository.save(
                Appointment.builder()
                        .patient(patient)
                        .doctor(doctor)
                        .consultRoom(consultRoom)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30))
                        .status(AppointmentStatus.SCHEDULED)
                        .build());

        Appointment appointment1 = appointmentRepository.save(
                Appointment.builder()
                        .patient(patient1)
                        .doctor(doctor)
                        .consultRoom(consultRoom)
                        .startTime(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0))
                        .endTime(LocalDateTime.now().plusDays(2).withHour(10).withMinute(30))
                        .status(AppointmentStatus.SCHEDULED)
                        .build());

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Enfermo")
                .notes("Sintomas leves")
                .createdAt(LocalDateTime.of(2025, 4, 18, 14, 0))
                .build();

        MedicalRecord medicalRecord1 = MedicalRecord.builder().
                patient(patient1).
                appointment(appointment1).
                diagnosis("Epicondilitis medial").
                notes("Dolor leve").
                createdAt(LocalDateTime.of(2024, 12, 20, 13, 55)).
                build();

        medicalRecordRepository.save(medicalRecord);
        medicalRecordRepository.save(medicalRecord1);

        List<MedicalRecord> results = medicalRecordRepository.findAll();

        assertEquals(2, results.size());

    }



    @Test
    void findAllByPatient_IdPatient() {
        Patient patient = patientRepository.save(
                Patient.builder()
                        .fullName("Juan Pérez")
                        .email("juan.perez@example.com")
                        .phone("+573001234567")
                        .build());

        Optional<Patient> resultPatient = patientRepository.findById(patient.getIdPatient());
        Long id = resultPatient.get().getIdPatient();


        // Crear Doctor
        Doctor doctor = doctorRepository.save(
                Doctor.builder()
                        .fullName("María González")
                        .email("maria.gonzalez@clinica.com")
                        .speciality("Cardiología")
                        .availableFrom(LocalTime.of(8, 0))
                        .availableTo(LocalTime.of(17, 0))
                        .build());

        // Crear ConsultRoom
        ConsultRoom consultRoom = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consulta 101")
                        .floor("1")
                        .description("Consultorio de Cardiología")
                        .build());

        // Crear Appointment
        Appointment appointment = appointmentRepository.save(
                Appointment.builder()
                        .patient(patient)
                        .doctor(doctor)
                        .consultRoom(consultRoom)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30))
                        .status(AppointmentStatus.SCHEDULED)
                        .build());

        Appointment appointment1 = appointmentRepository.save(
                Appointment.builder()
                        .patient(patient)
                        .doctor(doctor)
                        .consultRoom(consultRoom)
                        .startTime(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0))
                        .endTime(LocalDateTime.now().plusDays(2).withHour(10).withMinute(30))
                        .status(AppointmentStatus.SCHEDULED)
                        .build());

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Enfermo")
                .notes("Sintomas leves")
                .createdAt(LocalDateTime.of(2025, 4, 18, 14, 0))
                .build();

        MedicalRecord medicalRecord1 = MedicalRecord.builder().
                patient(patient).
                appointment(appointment1).
                diagnosis("Epicondilitis medial").
                notes("Dolor leve").
                createdAt(LocalDateTime.of(2024, 12, 20, 13, 55)).
                build();

        medicalRecordRepository.save(medicalRecord);
        medicalRecordRepository.save(medicalRecord1);

        List<MedicalRecord> results = medicalRecordRepository.findByPatient_IdPatient(id);

        assertEquals(2, results.size());
        assertFalse(results.isEmpty());

    }

    @Test
    void shouldDeleteMedicalRecord(){
        Patient patient = patientRepository.save(
                Patient.builder()
                        .fullName("Juan Pérez")
                        .email("juan.perez@example.com")
                        .phone("+573001234567")
                        .build());


        // Crear Doctor
        Doctor doctor = doctorRepository.save(
                Doctor.builder()
                        .fullName("Dra. María González")
                        .email("maria.gonzalez@clinica.com")
                        .speciality("Cardiología")
                        .availableFrom(LocalTime.of(8, 0))
                        .availableTo(LocalTime.of(17, 0))
                        .build());

        // Crear ConsultRoom
        ConsultRoom consultRoom = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consulta 101")
                        .floor("1")
                        .description("Consultorio de Cardiología")
                        .build());

        // Crear Appointment
        Appointment appointment = appointmentRepository.save(
                Appointment.builder()
                        .patient(patient)
                        .doctor(doctor)
                        .consultRoom(consultRoom)
                        .startTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0))
                        .endTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30))
                        .status(AppointmentStatus.SCHEDULED)
                        .build());

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .appointment(appointment)
                .diagnosis("Enfermo")
                .notes("Sintomas leves")
                .createdAt(LocalDateTime.of(2025, 4, 18, 14, 0))
                .build();

        MedicalRecord saved = medicalRecordRepository.save(medicalRecord);

        Long id = saved.getIdMedicalRecord();

        medicalRecordRepository.deleteById(id);

        assertFalse(medicalRecordRepository.findById(id).isPresent());
    }
}