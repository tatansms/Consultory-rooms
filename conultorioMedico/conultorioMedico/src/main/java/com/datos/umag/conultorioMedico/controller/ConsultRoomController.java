package com.datos.umag.conultorioMedico.controller;

import com.datos.umag.conultorioMedico.dto.ConsultRoomDTO;
import com.datos.umag.conultorioMedico.service.IService.IConsultRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class ConsultRoomController {

    private final IConsultRoomService consultRoomService;

    @Autowired
    public ConsultRoomController(IConsultRoomService consultRoomService) {
        this.consultRoomService = consultRoomService;
    }

    @GetMapping
    public ResponseEntity<List<ConsultRoomDTO>> getAllRooms() {
        return ResponseEntity.ok(consultRoomService.getAllConsultRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultRoomDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(consultRoomService.getConsultRoomById(id));
    }

    @PostMapping
    public ResponseEntity<ConsultRoomDTO> createRoom(@Valid @RequestBody ConsultRoomDTO consultRoomDTO) {
        return new ResponseEntity<>(consultRoomService.createConsultRoom(consultRoomDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultRoomDTO> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody ConsultRoomDTO consultRoomDTO) {
        return ResponseEntity.ok(consultRoomService.updateConsultRoom(id, consultRoomDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        consultRoomService.deleteConsultRoom(id);
        return ResponseEntity.noContent().build();
    }
}
