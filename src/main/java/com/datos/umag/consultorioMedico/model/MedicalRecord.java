package com.datos.umag.consultorioMedico.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "medical_record")
public class MedicalRecord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicalRecord;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_patient", referencedColumnName = "idPatient")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "id_appointment", referencedColumnName = "idAppointment", nullable = false)
    private Appointment appointment;

    @NotNull
    @Column(nullable = false)
    @Size(min = 5, max = 100)
    private String diagnosis;

    @Column
    @Size(max = 100)
    private String notes;

    @Column
    private LocalDateTime createdAt;

}