package com.datos.umag.consultorioMedico.mapper;

import com.datos.umag.consultorioMedico.dto.request.ConsultRoomDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.ConsultRoomDtoResponse;
import com.datos.umag.consultorioMedico.model.ConsultRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {

    @Mapping(source = "idConsultRoom", target = "id")
    ConsultRoomDtoResponse toConsultRoomDtoResponse(ConsultRoom consultRoom);

    ConsultRoom toEntity(ConsultRoomDtoRequest consultRoomDto);


    void updateConsultRoomFromDto(ConsultRoomDtoRequest dto, @MappingTarget ConsultRoom consultRoom);
}