package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.MedicalRecordDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.MedicalRecordDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @GetMapping
    public ResponseEntity<List<MedicalRecordDtoResponse>> getAllMedicalRecords(){
        return ResponseEntity.ok(medicalRecordService.findAllMedicalRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDtoResponse> getMedicalRecordById(@PathVariable Long id){
        return ResponseEntity.ok(medicalRecordService.findById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordDtoResponse>> getMedicalRecordsByPatientId(@PathVariable Long patientId){
        return ResponseEntity.ok(medicalRecordService.findMedicalRecordsByPatient(patientId));
    }

    @PostMapping
    public ResponseEntity<MedicalRecordDtoResponse> createMedicalRecord(@Valid @RequestBody MedicalRecordDtoRequest medicalRecordDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.saveMedicalRecord(medicalRecordDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id){
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }


}