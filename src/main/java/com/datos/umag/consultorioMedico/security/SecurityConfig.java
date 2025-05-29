package com.datos.umag.consultorioMedico.security;

import com.datos.umag.consultorioMedico.security.jwt.JwtEntryPoint;
import com.datos.umag.consultorioMedico.security.jwt.JwtFilter;
import com.datos.umag.consultorioMedico.security.service.JpaUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List; // Para CORS


@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring
@EnableMethodSecurity // Habilita seguridad a nivel de método (ej. @PreAuthorize)
@RequiredArgsConstructor // Lombok para inyección de dependencias
public class SecurityConfig {

    private final JwtEntryPoint unauthorizedHandler; // Para manejar errores de autenticación

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter, JpaUserDetailService jpaUserDetailService) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No usar sesiones HTTP
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler)) // Manejo de excepciones de autenticación
                .authorizeHttpRequests(auth -> auth
                        // ¡CRÍTICO! Permite acceso público a todas las rutas bajo /api/auth/
                        .requestMatchers("/api/auth/**").permitAll()
                        // Aquí puedes añadir otras rutas públicas si las tienes, por ejemplo, Swagger UI
                        // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
                )
                .authenticationProvider(authenticationProvider(jpaUserDetailService)) // ¡CRÍTICO! Añadir el AuthenticationProvider
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Añadir el filtro JWT
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(JpaUserDetailService jpaUserDetailService) { // Inyecta tu UserDetailsService
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jpaUserDetailService); // Usa tu JpaUserDetailService
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Usa tu PasswordEncoder
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para la configuración de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000")); // Añade los orígenes de tu frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true); // Permite enviar cookies o Authorization header

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}