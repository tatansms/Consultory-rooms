package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.ConsultRoomDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.ConsultRoomDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.ConsultRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ConsultRoomController {

    private final ConsultRoomService consultRoomService;

    @GetMapping
    public ResponseEntity<List<ConsultRoomDtoResponse>> getAllConsultRooms() {
        return ResponseEntity.ok(consultRoomService.findAllConsultRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultRoomDtoResponse> getConsultRoomById(@PathVariable Long id){
        return ResponseEntity.ok(consultRoomService.findConsultRoomById(id));
    }

    @PostMapping
    public ResponseEntity<ConsultRoomDtoResponse> createConsultRoom(@Valid @RequestBody ConsultRoomDtoRequest consultRoomDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(consultRoomService.saveConsultRoom(consultRoomDtoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultRoomDtoResponse> updateConsultRoom(@PathVariable Long id, @Valid @RequestBody ConsultRoomDtoRequest consultRoomDtoRequest){
        return ResponseEntity.ok(consultRoomService.updateConsultRoom(id, consultRoomDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultRoom(@PathVariable Long id){
        consultRoomService.deleteConsultRoom(id);
        return ResponseEntity.noContent().build();
    }

}