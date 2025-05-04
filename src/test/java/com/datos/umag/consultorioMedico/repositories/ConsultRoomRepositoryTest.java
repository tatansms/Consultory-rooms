package com.datos.umag.consultorioMedico.repositories;

import com.datos.umag.consultorioMedico.model.ConsultRoom;
import com.datos.umag.consultorioMedico.repository.ConsultRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class ConsultRoomRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");


    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    @Test
    void shouldSaveAndFindConsultRoom(){

        ConsultRoom consultRoom = ConsultRoom.builder().name("Consultorio 101").floor("1").description("Consultas de orden general").build();
        ConsultRoom saved = consultRoomRepository.save(consultRoom);
        Optional<ConsultRoom> result = consultRoomRepository.findById(saved.getIdConsultRoom());

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getFloor());
    }

    @Test
    void shouldFindAllConsultsRooms(){
        ConsultRoom consultRoom1 = ConsultRoom.builder()
                .name("Consultorio 101")
                .floor("1")
                .description("Consultas de orden general")
                .build();
        ConsultRoom consultRoom2 = ConsultRoom.builder()
                .name("Pediatría 205")
                .floor("2")
                .description("Sala de pediatría")
                .build();

        consultRoomRepository.save(consultRoom1);
        consultRoomRepository.save((consultRoom2));

        List<ConsultRoom> consultRooms = consultRoomRepository.findAll();

        assertEquals(2, consultRooms.size());
        assertTrue(consultRooms.contains(consultRoom1));

    }

    @Test
    void shouldUpdateConsultRoom(){
        ConsultRoom consultRoom1 = ConsultRoom.builder()
                .name("Consultorio 101")
                .floor("1")
                .description("Consultas de orden general")
                .build();

        consultRoom1.setName("Oftomología 101");

        ConsultRoom result = consultRoomRepository.save(consultRoom1);

        assertEquals("Oftomología 101", result.getName());
    }

    @Test
    void shouldDeleteConsultRoom(){
        ConsultRoom consultRoom1 = ConsultRoom.builder()
                .name("Consultorio 101")
                .floor("1")
                .description("Consultas de orden general")
                .build();

        ConsultRoom saved = consultRoomRepository.save(consultRoom1);
        Long id = saved.getIdConsultRoom();

        consultRoomRepository.deleteById(id);

        assertFalse(consultRoomRepository.findById(id).isPresent());


    }
}