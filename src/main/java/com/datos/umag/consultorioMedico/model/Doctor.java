package com.datos.umag.consultorioMedico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoctor;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointment;

    @Column
    @NotNull
    @NotBlank
    private String fullName;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String speciality;

    @Column(nullable = false)
    private LocalTime availableFrom;

    @Column(nullable = false)
    private LocalTime availableTo;



}