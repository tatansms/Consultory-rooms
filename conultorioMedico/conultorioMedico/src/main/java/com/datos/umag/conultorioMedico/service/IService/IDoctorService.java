package com.datos.umag.conultorioMedico.service.IService;

import com.datos.umag.conultorioMedico.dto.DoctorDTO;

import java.util.List;

public interface IDoctorService {
    List<DoctorDTO> getAll();
    DoctorDTO getById(Long id);
    DoctorDTO create(DoctorDTO dto);
    DoctorDTO update(Long id, DoctorDTO dto);
    void delete(Long id);
}
