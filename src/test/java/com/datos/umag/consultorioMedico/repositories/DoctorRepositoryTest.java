package com.datos.umag.consultorioMedico.repositories;

import com.datos.umag.consultorioMedico.model.Doctor;
import com.datos.umag.consultorioMedico.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class DoctorRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");


    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void shouldSaveAndFindDoctor(){

        Doctor doctor = Doctor.builder()
                .fullName("Juan Pedro")
                .email("doctor@test.com")
                .speciality("cardiología")
                .availableFrom(LocalTime.parse("06:00:00"))
                .availableTo(LocalTime.parse("16:00:00"))
                .build();

        Doctor saved = doctorRepository.save(doctor);

        Optional<Doctor> result = doctorRepository.findById(saved.getIdDoctor());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("doctor@test.com", result.get().getEmail());

    }

    @Test
    void shouldFindAllPatient(){

        Doctor doctor = Doctor.builder()
                .fullName("Juan Pedro")
                .email("doctor@test.com")
                .speciality("cardiología")
                .availableFrom(LocalTime.parse("06:00:00"))
                .availableTo(LocalTime.parse("16:00:00"))
                .build();

        Doctor doctor2 = Doctor.builder()
                .fullName("Luis Gonzales")
                .email("medico@test.com")
                .speciality("urología")
                .availableFrom(LocalTime.parse("08:00:00"))
                .availableTo(LocalTime.parse("18:00:00"))
                .build();

        doctorRepository.save(doctor);
        doctorRepository.save(doctor2);

        List<Doctor> result = doctorRepository.findAll();

        Assertions.assertEquals(2, result.size());

    }

    @Test
    void shouldUpdateDoctor(){

        Doctor doctor = Doctor.builder()
                .fullName("Juan Pedro")
                .email("doctor@test.com")
                .speciality("cardiología")
                .availableFrom(LocalTime.parse("06:00:00"))
                .availableTo(LocalTime.parse("16:00:00"))
                .build();

        doctorRepository.save(doctor);

        doctor.setEmail("nuevo@test.com");
        Doctor updated = doctorRepository.save(doctor);

        Assertions.assertEquals("nuevo@test.com", updated.getEmail());

    }

    @Test
    void shouldDeleteDoctor(){

        Doctor doctor = Doctor.builder()
                .fullName("Juan Pedro")
                .email("doctor@test.com")
                .speciality("cardiología")
                .availableFrom(LocalTime.parse("06:00:00"))
                .availableTo(LocalTime.parse("16:00:00"))
                .build();

        Doctor saved = doctorRepository.save(doctor);
        Long id = saved.getIdDoctor();
        doctorRepository.deleteById(id);

        Assertions.assertFalse(doctorRepository.findById(id).isPresent());

    }

}