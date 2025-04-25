package com.datos.umag.conultorioMedico.mapper;

import com.datos.umag.conultorioMedico.dto.ConsultRoomDTO;
import com.datos.umag.conultorioMedico.model.ConsultRoom;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {

    ConsultRoomMapper INSTANCE = Mappers.getMapper(ConsultRoomMapper.class);

    ConsultRoomDTO toDTO(ConsultRoom consultRoom);

    ConsultRoom toEntity(ConsultRoomDTO consultRoomDTO);

    void updateEntityFromDTO(ConsultRoomDTO consultRoomDTO, @MappingTarget ConsultRoom consultRoom);
}
