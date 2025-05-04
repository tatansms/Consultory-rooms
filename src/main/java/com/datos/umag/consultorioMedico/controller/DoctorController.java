package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.request.DoctorDtoRequest;
import com.datos.umag.consultorioMedico.dto.response.DoctorDtoResponse;
import com.datos.umag.consultorioMedico.service.IService.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDtoResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDtoResponse> getDoctorById(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.findDoctorById(id));
    }

    @PostMapping
    public ResponseEntity<DoctorDtoResponse> createDoctor(@Valid @RequestBody DoctorDtoRequest doctorDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.saveDoctor(doctorDtoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDtoResponse> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDtoRequest doctorDtoRequest){
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }


}