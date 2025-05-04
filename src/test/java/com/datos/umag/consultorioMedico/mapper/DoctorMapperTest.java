package com.datos.umag.consultorioMedico.mapper;

import com.datos.umag.consultorioMedico.dto.request.DoctorDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.DoctorDtoResponse;
import com.datos.umag.consultorioMedico.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class DoctorMapperTest {

    private DoctorMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DoctorMapperImpl();
    }

    @Test
    void toDoctorDtoResponse_shouldMapAllFields() {
        Doctor doctor = Doctor.builder()
                .idDoctor(1L)
                .fullName("Dr. House")
                .email("house@princeton.com")
                .speciality("Diagnóstico")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(16, 0))
                .build();

        DoctorDtoResponse dto = mapper.toDoctorDtoResponse(doctor);

        assertThat(dto.idDoctor()).isEqualTo(doctor.getIdDoctor());
        assertThat(dto.fullName()).isEqualTo(doctor.getFullName());
        assertThat(dto.email()).isEqualTo(doctor.getEmail());
        assertThat(dto.speciality()).isEqualTo(doctor.getSpeciality());
        assertThat(dto.availableFrom()).isEqualTo(doctor.getAvailableFrom());
        assertThat(dto.availableTo()).isEqualTo(doctor.getAvailableTo());
    }

    @Test
    void toEntity_shouldMapFieldsExceptId() {
        DoctorDtoRequest dtoRequest = new DoctorDtoRequest(
                "Dra. Ana López",
                "ana.lopez@clinic.com",
                "Pediatría",
                LocalTime.of(9, 30),
                LocalTime.of(18, 0)
        );

        Doctor entity = mapper.toEntity(dtoRequest);

        assertThat(entity.getIdDoctor()).isNull();
        assertThat(entity.getFullName()).isEqualTo(dtoRequest.fullName());
        assertThat(entity.getEmail()).isEqualTo(dtoRequest.email());
        assertThat(entity.getSpeciality()).isEqualTo(dtoRequest.speciality());
        assertThat(entity.getAvailableFrom()).isEqualTo(dtoRequest.availableFrom());
        assertThat(entity.getAvailableTo()).isEqualTo(dtoRequest.availableTo());
    }
}