package com.datos.umag.consultorioMedico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "consult_room")
public class ConsultRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsultRoom;

    @OneToMany(mappedBy = "consultRoom")
    private List<Appointment> appointments;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Positive
    private String floor;

    @Column
    @Size(max = 1000)
    private String description;
}