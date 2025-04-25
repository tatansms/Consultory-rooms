package com.datos.umag.conultorioMedico.util;



    public enum AppointmentStatus {
        SCHEDULED,    // Cita programada (estado inicial)
        COMPLETED,    // Cita finalizada (permite registrar MedicalRecord)
        CANCELED      // Cita cancelada (no permite cambios)
    }
