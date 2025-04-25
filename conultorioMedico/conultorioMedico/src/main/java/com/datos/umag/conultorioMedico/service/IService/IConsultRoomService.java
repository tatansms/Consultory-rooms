package com.datos.umag.conultorioMedico.service.IService;

import com.datos.umag.conultorioMedico.dto.ConsultRoomDTO;

import java.util.List;

public interface IConsultRoomService {
    ConsultRoomDTO getConsultRoomById(Long id);
    List<ConsultRoomDTO> getAllConsultRooms();
    ConsultRoomDTO createConsultRoom(ConsultRoomDTO consultRoomDTO);
    ConsultRoomDTO updateConsultRoom(Long id, ConsultRoomDTO consultRoomDTO);
    void deleteConsultRoom(Long id);
}

