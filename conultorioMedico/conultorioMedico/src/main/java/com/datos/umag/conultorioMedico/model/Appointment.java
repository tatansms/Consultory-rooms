package com.datos.umag.conultorioMedico.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import com.datos.umag.conultorioMedico.util.AppointmentStatus;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private ConsultRoom consultRoom;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status; // Enum: SCHEDULED, COMPLETED, CANCELED
    
}
