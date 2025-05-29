package com.datos.umag.consultorioMedico.model;

import com.datos.umag.consultorioMedico.util.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
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

public class Appointment {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long idAppointment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_patient", referencedColumnName = "idPatient")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_doctor", referencedColumnName = "idDoctor")
    private Doctor doctor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_consultRoom", referencedColumnName = "idConsultRoom")
    private ConsultRoom consultRoom;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalRecord medicalRecord;

    @Column(nullable = false)
    @Future
    private LocalDateTime startTime;

    @Column(nullable = false)
    @Future
    private LocalDateTime endTime;

    @Column
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

}