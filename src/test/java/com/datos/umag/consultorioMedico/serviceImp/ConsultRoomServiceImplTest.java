package com.datos.umag.consultorioMedico.serviceImp;

import com.datos.umag.consultorioMedico.dto.request.ConsultRoomDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.ConsultRoomDtoResponse;
import com.datos.umag.consultorioMedico.exception.notFound.ConsultRoomNotFoundException;
import com.datos.umag.consultorioMedico.mapper.ConsultRoomMapper;
import com.datos.umag.consultorioMedico.model.ConsultRoom;
import com.datos.umag.consultorioMedico.repository.ConsultRoomRepository;
import com.datos.umag.consultorioMedico.service.ConsultRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConsultRoomServiceImplTest {

    @Mock
    private ConsultRoomRepository consultRoomRepository;
    @Mock
    private ConsultRoomMapper consultRoomMapper;

    @InjectMocks
    private ConsultRoomServiceImpl service;

    private ConsultRoom room;
    private ConsultRoomDtoRequest dtoRequest;
    private ConsultRoomDtoResponse dtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = ConsultRoom.builder()
                .idConsultRoom(5L)
                .name("Sala A")
                .floor("2")
                .description("Sala de reuniones")
                .build();
        dtoRequest = new ConsultRoomDtoRequest(
                "Sala B", "3", "Sala grande"
        );
        dtoResponse = new ConsultRoomDtoResponse(
                5L, room.getName(), room.getFloor(), room.getDescription()
        );
    }

    @Test
    void findAllConsultRooms_returnsMappedList() {
        when(consultRoomRepository.findAll()).thenReturn(List.of(room));
        when(consultRoomMapper.toConsultRoomDtoResponse(room)).thenReturn(dtoResponse);

        List<ConsultRoomDtoResponse> result = service.findAllConsultRooms();

        assertThat(result).containsExactly(dtoResponse);
        verify(consultRoomRepository).findAll();
        verify(consultRoomMapper).toConsultRoomDtoResponse(room);
    }

    @Test
    void findConsultRoomById_whenExists_returnsDto() {
        when(consultRoomRepository.findById(5L)).thenReturn(Optional.of(room));
        when(consultRoomMapper.toConsultRoomDtoResponse(room)).thenReturn(dtoResponse);

        ConsultRoomDtoResponse result = service.findConsultRoomById(5L);

        assertThat(result).isEqualTo(dtoResponse);
    }

    @Test
    void findConsultRoomById_whenNotFound_throwsException() {
        when(consultRoomRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ConsultRoomNotFoundException.class,
                () -> service.findConsultRoomById(99L));
    }

    @Test
    void saveConsultRoom_savesAndReturnsDto() {
        // mapper.toEntity
        when(consultRoomMapper.toEntity(dtoRequest)).thenReturn(room);
        // repository.save
        when(consultRoomRepository.save(room)).thenReturn(room);
        // mapper.toDTO
        when(consultRoomMapper.toConsultRoomDtoResponse(room)).thenReturn(dtoResponse);

        ConsultRoomDtoResponse result = service.saveConsultRoom(dtoRequest);

        assertThat(result).isEqualTo(dtoResponse);
        verify(consultRoomMapper).toEntity(dtoRequest);
        verify(consultRoomRepository).save(room);
        verify(consultRoomMapper).toConsultRoomDtoResponse(room);
    }

    @Test
    void updateConsultRoom_whenExists_updatesAndReturnsDto() {
        when(consultRoomRepository.findById(5L)).thenReturn(Optional.of(room));
        doNothing().when(consultRoomMapper).updateConsultRoomFromDto(dtoRequest, room);
        when(consultRoomRepository.save(room)).thenReturn(room);
        when(consultRoomMapper.toConsultRoomDtoResponse(room)).thenReturn(dtoResponse);

        ConsultRoomDtoResponse result = service.updateConsultRoom(5L, dtoRequest);

        assertThat(result).isEqualTo(dtoResponse);
        verify(consultRoomMapper).updateConsultRoomFromDto(dtoRequest, room);
        verify(consultRoomRepository).save(room);
    }

    @Test
    void updateConsultRoom_whenNotFound_throwsException() {
        when(consultRoomRepository.findById(7L)).thenReturn(Optional.empty());
        assertThrows(ConsultRoomNotFoundException.class,
                () -> service.updateConsultRoom(7L, dtoRequest));
    }

    @Test
    void deleteConsultRoom_whenNotExists_throwsException() {
        when(consultRoomRepository.existsById(10L)).thenReturn(false);
        assertThrows(ConsultRoomNotFoundException.class,
                () -> service.deleteConsultRoom(10L));
    }

    @Test
    void deleteConsultRoom_whenExists_deletes() {
        when(consultRoomRepository.existsById(5L)).thenReturn(true);

        service.deleteConsultRoom(5L);

        verify(consultRoomRepository).deleteById(5L);
    }
}