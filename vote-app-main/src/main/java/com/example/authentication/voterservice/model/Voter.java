package com.example.authentication.voterservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;  // ⬅️ CHANGÉ de LocalDateTime à LocalDate
import java.time.LocalDateTime;

@Entity
@Table(name = "voters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CIN is required")
    @Column(unique = true, nullable = false)
    private String cin;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;  // ⬅️ CHANGÉ ICI

    private String address;

    @Column(nullable = false)
    private Boolean hasVoted = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime registrationDate;

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
        if (hasVoted == null) {
            hasVoted = false;
        }
        if (isActive == null) {
            isActive = true;
        }
    }
}