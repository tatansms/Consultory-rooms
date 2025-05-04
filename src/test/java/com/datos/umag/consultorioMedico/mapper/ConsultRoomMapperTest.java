package com.datos.umag.consultorioMedico.mapper;

import com.datos.umag.consultorioMedico.dto.request.ConsultRoomDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.ConsultRoomDtoResponse;
import com.datos.umag.consultorioMedico.model.ConsultRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConsultRoomMapperTest {

    private ConsultRoomMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ConsultRoomMapperImpl();
    }

    @Test
    void testToConsultRoomDtoResponse() {
        ConsultRoom consultRoom = ConsultRoom.builder()
                .idConsultRoom(1L)
                .name("Sala de Cardiología")
                .floor("3")
                .description("Sala equipada para ecocardiogramas")
                .build();

        ConsultRoomDtoResponse dto = mapper.toConsultRoomDtoResponse(consultRoom);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Sala de Cardiología");
        assertThat(dto.floor()).isEqualTo("3");
        assertThat(dto.description()).isEqualTo("Sala equipada para ecocardiogramas");
    }

    @Test
    void testToEntity() {
        ConsultRoomDtoRequest dtoRequest = new ConsultRoomDtoRequest(
                "Sala de Rayos X",
                "2",
                "Sala con equipo de radiología digital"
        );

        ConsultRoom entity = mapper.toEntity(dtoRequest);

        assertThat(entity.getIdConsultRoom()).isNull();
        assertThat(entity.getName()).isEqualTo("Sala de Rayos X");
        assertThat(entity.getFloor()).isEqualTo("2");
        assertThat(entity.getDescription()).isEqualTo("Sala con equipo de radiología digital");
    }

    @Test
    void testUpdateConsultRoomFromDto() {
        ConsultRoom consultRoom = ConsultRoom.builder()
                .idConsultRoom(5L)
                .name("Sala vieja")
                .floor("1")
                .description("Descripción antigua")
                .build();

        ConsultRoomDtoRequest updateDto = new ConsultRoomDtoRequest(
                "Sala renovada",
                "4",
                "Nueva descripción con equipamiento moderno"
        );

        mapper.updateConsultRoomFromDto(updateDto, consultRoom);

        assertThat(consultRoom.getIdConsultRoom()).isEqualTo(5L);
        assertThat(consultRoom.getName()).isEqualTo("Sala renovada");
        assertThat(consultRoom.getFloor()).isEqualTo("4");
        assertThat(consultRoom.getDescription()).isEqualTo("Nueva descripción con equipamiento moderno");
    }
}