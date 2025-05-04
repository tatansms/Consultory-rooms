package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.PatientDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.PatientDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDtoResponse>> getAllRooms(){
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDtoResponse> getPatientById(@PathVariable Long id){
        return ResponseEntity.ok(patientService.findPatientById(id));
    }

    @PostMapping
    public ResponseEntity<PatientDtoResponse> createPatient(@RequestBody @Valid PatientDtoRequest patientDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.savePatient(patientDtoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDtoResponse> updatePatient(@PathVariable  Long id, @RequestBody @Valid PatientDtoRequest patientDtoRequest){
        return ResponseEntity.ok(patientService.updatePatient(id,patientDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}