package com.datos.umag.consultorioMedico.service;

import com.datos.umag.consultorioMedico.dto.request.ConsultRoomDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.ConsultRoomDtoResponse;
import com.datos.umag.consultorioMedico.exception.notFound.ConsultRoomNotFoundException;
import com.datos.umag.consultorioMedico.mapper.ConsultRoomMapper;
import com.datos.umag.consultorioMedico.model.ConsultRoom;
import com.datos.umag.consultorioMedico.repository.ConsultRoomRepository;
import com.datos.umag.consultorioMedico.service.IService.ConsultRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultRoomServiceImpl implements ConsultRoomService {

    private final ConsultRoomRepository consultRoomRepository; //
    private final ConsultRoomMapper consultRoomMapper;     //

    @Override
    public List<ConsultRoomDtoResponse> findAllConsultRooms() {
        return consultRoomRepository.findAll().stream()
                .map(consultRoomMapper::toConsultRoomDtoResponse)
                .toList();
    }

    @Override
    public ConsultRoomDtoResponse findConsultRoomById(Long id) {
        ConsultRoom consultRoom = consultRoomRepository.findById(id)
                .orElseThrow(() -> new ConsultRoomNotFoundException("Consult Room with ID: " + id + " Not Found"));

        return consultRoomMapper.toConsultRoomDtoResponse(consultRoom);
    }

    @Override
    public ConsultRoomDtoResponse saveConsultRoom(ConsultRoomDtoRequest consultRoomDtoRequest) {
        ConsultRoom toBeSaved = consultRoomMapper.toEntity(consultRoomDtoRequest);
        return consultRoomMapper.toConsultRoomDtoResponse(consultRoomRepository.save(toBeSaved));
    }

    @Override
    public ConsultRoomDtoResponse updateConsultRoom(Long id, ConsultRoomDtoRequest consultRoomDtoRequest) {
        ConsultRoom consultRoom = consultRoomRepository.findById(id)
                .orElseThrow(() -> new ConsultRoomNotFoundException("Consult Room with ID: " + id + " Not Found"));

        consultRoomMapper.updateConsultRoomFromDto(consultRoomDtoRequest, consultRoom);

        return consultRoomMapper.toConsultRoomDtoResponse(consultRoomRepository.save(consultRoom));

    }

    @Override
    public void deleteConsultRoom(Long id) {

        if(!consultRoomRepository.existsById(id)){
            throw new ConsultRoomNotFoundException("Consult Room with ID: " + id + " Not Found");
        }

        consultRoomRepository.deleteById(id);
    }

}