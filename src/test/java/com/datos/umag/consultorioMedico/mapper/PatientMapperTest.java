package com.datos.umag.consultorioMedico.mapper;

import com.datos.umag.consultorioMedico.dto.request.PatientDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.PatientDtoResponse;
import com.datos.umag.consultorioMedico.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatientMapperTest {

    private PatientMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PatientMapperImpl();
    }

    @Test
    void toPatientDtoResponse_shouldMapAllFields() {
        Patient patient = Patient.builder()
                .idPatient(1L)
                .fullName("Luis Gómez")
                .email("luis.gomez@example.com")
                .phone("3001234567")
                .build();

        PatientDtoResponse dto = mapper.toPatientDtoResponse(patient);

        assertThat(dto.idPatient()).isEqualTo(patient.getIdPatient());
        assertThat(dto.fullName()).isEqualTo(patient.getFullName());
        assertThat(dto.email()).isEqualTo(patient.getEmail());
        assertThat(dto.phone()).isEqualTo(patient.getPhone());
    }

    @Test
    void toEntity_shouldMapFieldsExceptId() {
        PatientDtoRequest dtoRequest = new PatientDtoRequest(
                "Ana Torres",
                "ana.torres@example.com",
                "3117654321"
        );

        Patient entity = mapper.toEntity(dtoRequest);

        assertThat(entity.getIdPatient()).isNull();
        assertThat(entity.getFullName()).isEqualTo(dtoRequest.fullName());
        assertThat(entity.getEmail()).isEqualTo(dtoRequest.email());
        assertThat(entity.getPhone()).isEqualTo(dtoRequest.phone());
    }
}