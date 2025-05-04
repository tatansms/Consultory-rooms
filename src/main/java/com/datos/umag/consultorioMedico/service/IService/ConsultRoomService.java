package com.datos.umag.consultorioMedico.service.IService;

import com.datos.umag.consultorioMedico.dto.request.ConsultRoomDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.ConsultRoomDtoResponse;

import java.util.List;

public interface ConsultRoomService {

    List<ConsultRoomDtoResponse> findAllConsultRooms();
    ConsultRoomDtoResponse findConsultRoomById(Long id);
    ConsultRoomDtoResponse saveConsultRoom(ConsultRoomDtoRequest consultRoomDtoRequest);
    ConsultRoomDtoResponse updateConsultRoom(Long id, ConsultRoomDtoRequest consultRoomDtoRequest);
    void deleteConsultRoom(Long id);
}