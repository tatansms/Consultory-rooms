package com.datos.umag.conultorioMedico.repository;
import com.datos.umag.conultorioMedico.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
    SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
    FROM Appointment a
    WHERE 
        (a.doctor.id = :doctorId OR a.consultRoom.id = :roomId)
        AND a.id <> COALESCE(:excludeId, -1)
        AND (
            (a.startTime < :end AND a.endTime > :start)
        )
""")
    boolean existsConflict(@Param("doctorId") Long doctorId,
                           @Param("roomId") Long roomId,
                           @Param("start") LocalDateTime start,
                           @Param("end") LocalDateTime end,
                           @Param("excludeId") Long excludeId);

}
