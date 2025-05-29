package com.datos.umag.consultorioMedico.repository;

import com.datos.umag.consultorioMedico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// En tu UserRepository.java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username); //  útil para el registro
    Boolean existsByEmail(String email);
}