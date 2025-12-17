package org.vote.voteservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {
    private Long id;
    private String cin;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private Boolean hasVoted;
    private Boolean isActive;
    private LocalDateTime registrationDate;
}