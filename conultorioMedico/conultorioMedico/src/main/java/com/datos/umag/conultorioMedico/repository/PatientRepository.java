package com.datos.umag.conultorioMedico.repository;

import com.datos.umag.conultorioMedico.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
