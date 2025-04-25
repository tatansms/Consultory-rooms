package com.datos.umag.conultorioMedico.service;

import com.datos.umag.conultorioMedico.dto.ConsultRoomDTO;
import com.datos.umag.conultorioMedico.mapper.ConsultRoomMapper;
import com.datos.umag.conultorioMedico.model.ConsultRoom;
import com.datos.umag.conultorioMedico.repository.ConsultRoomRepository;
import com.datos.umag.conultorioMedico.service.IService.IConsultRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultRoomService implements IConsultRoomService {

    private final ConsultRoomRepository consultRoomRepository;
    private final ConsultRoomMapper consultRoomMapper;

    @Autowired
    public ConsultRoomService(ConsultRoomRepository consultRoomRepository, ConsultRoomMapper consultRoomMapper) {
        this.consultRoomRepository = consultRoomRepository;
        this.consultRoomMapper = consultRoomMapper;
    }

    @Override
    public ConsultRoomDTO getConsultRoomById(Long id) {
        ConsultRoom room = consultRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consult Room not found"));
        return consultRoomMapper.toDTO(room);
    }

    @Override
    public List<ConsultRoomDTO> getAllConsultRooms() {
        List<ConsultRoom> rooms = consultRoomRepository.findAll();
        return rooms.stream()
                .map(consultRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultRoomDTO createConsultRoom(ConsultRoomDTO consultRoomDTO) {
        ConsultRoom room = consultRoomMapper.toEntity(consultRoomDTO);
        ConsultRoom savedRoom = consultRoomRepository.save(room);
        return consultRoomMapper.toDTO(savedRoom);
    }

    @Override
    public ConsultRoomDTO updateConsultRoom(Long id, ConsultRoomDTO consultRoomDTO) {
        ConsultRoom existingRoom = consultRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consult Room not found"));
        consultRoomMapper.updateEntityFromDTO(consultRoomDTO, existingRoom);
        ConsultRoom updatedRoom = consultRoomRepository.save(existingRoom);
        return consultRoomMapper.toDTO(updatedRoom);
    }

    @Override
    public void deleteConsultRoom(Long id) {
        if (!consultRoomRepository.existsById(id)) {
            throw new RuntimeException("Consult Room not found");
        }
        consultRoomRepository.deleteById(id);
    }
}
