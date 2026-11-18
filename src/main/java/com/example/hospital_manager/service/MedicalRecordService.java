package com.example.hospital_manager.service;



import com.example.hospital_manager.dto.*;
import com.example.hospital_manager.exception.ApiException;
import com.example.hospital_manager.model.*;
import com.example.hospital_manager.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(
            MedicalRecordRepository medicalRecordRepository,
            PatientRepository patientRepository) {

        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    public MedicalRecordResponse createRecord(
            MedicalRecordRequest request,
            AppUser doctor) {

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ApiException(
                        "Patient not found",
                        HttpStatus.NOT_FOUND
                ));

        if (doctor.getRole() != Role.DOCTOR) {
            throw new ApiException(
                    "Only doctors can create medical records",
                    HttpStatus.FORBIDDEN
            );
        }

        MedicalRecord record = MedicalRecord.builder()
                .patient(patient)
                .doctor(doctor)
                .diagnosis(request.diagnosis())
                .prescription(request.prescription())
                .build();

        return toResponse(medicalRecordRepository.save(record));
    }

    public List<MedicalRecordResponse> getPatientRecords(Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new ApiException(
                    "Patient not found",
                    HttpStatus.NOT_FOUND
            );
        }

        return medicalRecordRepository.findByPatientId(patientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MedicalRecordResponse> getAllRecords() {
        return medicalRecordRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private MedicalRecordResponse toResponse(MedicalRecord record) {

        return new MedicalRecordResponse(
                record.getId(),
                record.getPatient().getId(),
                record.getPatient().getFullName(),
                record.getDoctor().getId(),
                record.getDoctor().getUsername(),
                record.getDiagnosis(),
                record.getPrescription(),
                record.getCreatedAt()
        );
    }
}
