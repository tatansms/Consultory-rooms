package com.datos.umag.consultorioMedico.controller;

import com.datos.umag.consultorioMedico.dto.JwtResponse;
import com.datos.umag.consultorioMedico.dto.LoginRequest;
import com.datos.umag.consultorioMedico.dto.RegisterRequest; // ¡Importar RegisterRequest!
import com.datos.umag.consultorioMedico.model.Role; // Importar Role
import com.datos.umag.consultorioMedico.model.User; // Importar User
import com.datos.umag.consultorioMedico.repository.RoleRepository; // Importar RoleRepository
import com.datos.umag.consultorioMedico.repository.UserRepository; // Importar UserRepository
import com.datos.umag.consultorioMedico.security.jwt.JwtUtil;
import com.datos.umag.consultorioMedico.security.service.UserInfoDetail;
import jakarta.validation.Valid; // Asegúrate de que esta importación exista
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // Importar HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder; // Importar
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder
import org.springframework.web.bind.annotation.*;

import java.util.HashSet; // Importar HashSet
import java.util.List; // Importar List
import java.util.Set; // Importar Set
import java.util.stream.Collectors; // Importar Collectors

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository; // Necesario para el registro
    private final RoleRepository roleRepository; // Necesario para el registro
    private final PasswordEncoder passwordEncoder; // Necesario para el registro

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateToken(authentication.getName());

            UserInfoDetail userDetails = (UserInfoDetail) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            // ¡IMPORTANTE! JwtResponse debe tener los campos id, username, email, roles
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña inválidos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.username())) {
            return ResponseEntity.badRequest().body("Error: ¡El nombre de usuario ya está en uso!");
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            return ResponseEntity.badRequest().body("Error: ¡El email ya está en uso!");
        }

        // Crear nuevo usuario
        User user = new User();
        user.setUsername(signUpRequest.username());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));

        Set<String> strRoles = signUpRequest.roles(); // Esto viene del DTO
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(roleNameFromRequest -> { // Renombré 'role' a 'roleNameFromRequest' para claridad
                switch (roleNameFromRequest.toLowerCase()) { // ¡Convierte a minúsculas para el switch!
                    case "admin":
                        Role adminRole = roleRepository.findByName("ADMIN") // Busca "ADMIN" (mayúsculas en DB)
                                .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no encontrado."));
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role userRole = roleRepository.findByName("USER") // Busca "USER" (mayúsculas en DB)
                                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));
                        roles.add(userRole);
                        break;
                    default:
                        // Manejo de rol no reconocido: Asignar USER por defecto o lanzar error
                        Role defaultUserRole = roleRepository.findByName("USER")
                                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado como default."));
                        roles.add(defaultUserRole);
                        break; // Asegúrate de tener un break aquí
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("¡Usuario registrado exitosamente!");
    }
}