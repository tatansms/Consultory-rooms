package com.datos.umag.consultorioMedico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data // O @Getter, @Setter
@AllArgsConstructor // Para el constructor con todos los campos
public class JwtResponse {
    private String token;
    private String type = "Bearer"; // Buen estándar
    private Long id;
    private String username;
    private String email;
    private List<String> roles; // Lista de strings para los nombres de roles

    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    // Puedes mantener este si lo usas en otras partes
    public JwtResponse(String token) {
        this.token = token;
    }
}