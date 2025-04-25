

package com.datos.umag.conultorioMedico.service;

import com.datos.umag.conultorioMedico.dto.DoctorDTO;
import com.datos.umag.conultorioMedico.exception.ResourceNotFoundException;
import com.datos.umag.conultorioMedico.mapper.DoctorMapper;
import com.datos.umag.conultorioMedico.model.Doctor;
import com.datos.umag.conultorioMedico.repository.DoctorRepository;
import com.datos.umag.conultorioMedico.service.IService.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorDTO> getAll() {
        return doctorMapper.toDtoList(doctorRepository.findAll());
    }

    @Override
    public DoctorDTO getById(Long id) {
        return doctorMapper.toDto(doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado")));
    }

    @Override
    public DoctorDTO create(DoctorDTO dto) {
        Doctor doctor = doctorMapper.toEntity(dto);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDTO update(Long id, DoctorDTO dto) {
        Doctor existing = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado"));
        Doctor updated = doctorMapper.toEntity(dto);
        updated.setId(existing.getId());
        return doctorMapper.toDto(doctorRepository.save(updated));
    }

    @Override
    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor no encontrado");
        }
        doctorRepository.deleteById(id);
    }



}
