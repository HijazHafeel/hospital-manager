package com.example.hospital_manager.config;



import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.hospital_manager.model.AppUser;
import com.example.hospital_manager.model.Patient;
import com.example.hospital_manager.model.Role;
import com.example.hospital_manager.repository.PatientRepository;
import com.example.hospital_manager.repository.UserRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UserRepository userRepository,
            PatientRepository patientRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(AppUser.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("Admin12345"))
                        .role(Role.ADMIN)
                        .build());
            }

            if (!userRepository.existsByUsername("doctor")) {
                userRepository.save(AppUser.builder()
                        .username("doctor")
                        .password(passwordEncoder.encode("Doctor12345"))
                        .role(Role.DOCTOR)
                        .build());
            }

            if (!userRepository.existsByUsername("nurse")) {
                userRepository.save(AppUser.builder()
                        .username("nurse")
                        .password(passwordEncoder.encode("Nurse12345"))
                        .role(Role.NURSE)
                        .build());
            }

            if (!userRepository.existsByUsername("receptionist")) {
                userRepository.save(AppUser.builder()
                        .username("receptionist")
                        .password(passwordEncoder.encode("Reception12345"))
                        .role(Role.RECEPTIONIST)
                        .build());
            }

            if (!patientRepository.existsByEmail("patient@example.com")) {
                patientRepository.save(Patient.builder()
                        .fullName("Sample Patient")
                        .dateOfBirth(LocalDate.of(2000, 1, 15))
                        .gender("Male")
                        .phone("0771234567")
                        .email("patient@example.com")
                        .address("Colombo, Sri Lanka")
                        .build());
            }
        };
    }
}