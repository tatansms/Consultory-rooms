package com.datos.umag.conultorioMedico.controller;

import com.datos.umag.conultorioMedico.dto.MedicalRecordDTO;
import com.datos.umag.conultorioMedico.service.IService.IMedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    private final IMedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(IMedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDTO>> getAllRecords() {
        return ResponseEntity.ok(medicalRecordService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordDTO>> getRecordsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalRecordService.getByPatientId(patientId));
    }

    @PostMapping
    public ResponseEntity<MedicalRecordDTO> createRecord(@Valid @RequestBody MedicalRecordDTO dto) {
        return new ResponseEntity<>(medicalRecordService.create(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        medicalRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
