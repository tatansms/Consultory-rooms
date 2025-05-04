package com.datos.umag.consultorioMedico.repository;

import com.datos.umag.consultorioMedico.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);
}
