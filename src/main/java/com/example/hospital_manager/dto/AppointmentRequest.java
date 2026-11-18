package com.example.hospital_manager.dto;



import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AppointmentRequest(

        @NotNull(message = "Patient ID is required")
        Long patientId,

        @NotNull(message = "Doctor ID is required")
        Long doctorId,

        @NotNull(message = "Appointment date and time are required")
        @Future(message = "Appointment must be in the future")
        LocalDateTime appointmentDateTime,

        @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
        String notes

) {}