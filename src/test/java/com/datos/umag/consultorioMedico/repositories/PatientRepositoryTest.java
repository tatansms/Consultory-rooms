package com.datos.umag.consultorioMedico.repositories;

import com.datos.umag.consultorioMedico.model.Patient;
import com.datos.umag.consultorioMedico.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void shouldSaveAndFindPatient(){
        Patient patient = Patient.builder().fullName("Pedro Pérez").email("prueba@test.com").phone("3204567890").build();
        Patient saved = patientRepository.save(patient);

        Optional<Patient> result = patientRepository.findById(saved.getIdPatient());

        Assertions.assertEquals("Pedro Pérez", result.get().getFullName());
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void shouldFindAllPatient(){
        patientRepository.save(Patient.builder().fullName("Andrés Escobar").email("test@gmail.com").phone("3103334455").build());
        patientRepository.save(Patient.builder().fullName("Mateo Angulo").email("mateo@test.com").phone("3001112233").build());

        List<Patient> patients = patientRepository.findAll();

        Assertions.assertEquals(2, patients.size());


    }
    @Test
    void findByEmail() {

        Patient patient2 = Patient.builder().fullName("Juan Camilo").email("pilar@test.com").phone("3204567800").build();

        patientRepository.save(patient2);
        Patient result = patientRepository.findByEmail("pilar@test.com");
        Assertions.assertEquals("3204567800", result.getPhone());


    }

    @Test
    void shouldUpdatePatient(){
        Patient patient = Patient.builder().fullName("Pedro Pérez").email("prueba@test.com").phone("3204567890").build();
        patient.setPhone("3103838223");
        Patient result = patientRepository.save(patient);
        Assertions.assertEquals("3103838223", result.getPhone());

    }

    @Test
    void shouldDeletePatient(){
        Patient patient = Patient.builder().fullName("Pedro Pérez").email("prueba@test.com").phone("3204567890").build();
        Patient saved = patientRepository.save(patient);
        Long id = saved.getIdPatient();
        patientRepository.deleteById(id);

        Assertions.assertFalse(patientRepository.findById(id).isPresent());

    }
}