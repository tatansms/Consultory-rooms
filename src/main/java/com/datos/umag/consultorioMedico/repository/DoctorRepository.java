package com.datos.umag.consultorioMedico.repository;

import com.datos.umag.consultorioMedico.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}