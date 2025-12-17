package com.example.authentication.voterservice;

import com.example.authentication.voterservice.model.Voter;
import com.example.authentication.voterservice.repository.VoterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class VoterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoterServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initVoters(VoterRepository voterRepository) {
        return args -> {
            // Vérifier si des électeurs existent déjà
            if (voterRepository.count() == 0) {
                System.out.println("🔄 Initialisation des électeurs...");

                voterRepository.save(Voter.builder()
                        .cin("AB123456")
                        .firstName("Aicha")
                        .lastName("Bennani")
                        .email("aicha.bennani@email.com")
                        .dateOfBirth(LocalDate.of(1990, 5, 15))
                        .address("123 Rue Hassan II, Casablanca")
                        .hasVoted(false)
                        .isActive(true)
                        .registrationDate(LocalDateTime.now())
                        .build());

                voterRepository.save(Voter.builder()
                        .cin("CD789012")
                        .firstName("Fatima")
                        .lastName("Alaoui")
                        .email("fatima.alaoui@email.com")
                        .dateOfBirth(LocalDate.of(1985, 8, 22))
                        .address("456 Avenue Mohammed V, Rabat")
                        .hasVoted(false)
                        .isActive(true)
                        .registrationDate(LocalDateTime.now())
                        .build());

                voterRepository.save(Voter.builder()
                        .cin("EF345678")
                        .firstName("Kawtar")
                        .lastName("El Idrissi")
                        .email("kawtar.idrissi@email.com")
                        .dateOfBirth(LocalDate.of(1992, 11, 30))
                        .address("789 Boulevard Zerktouni, Marrakech")
                        .hasVoted(false)
                        .isActive(true)
                        .registrationDate(LocalDateTime.now())
                        .build());

                voterRepository.save(Voter.builder()
                        .cin("GH901234")
                        .firstName("Khadija")
                        .lastName("Fassi")
                        .email("khadija.fassi@email.com")
                        .dateOfBirth(LocalDate.of(1988, 3, 10))
                        .address("321 Rue de la Liberté, Fès")
                        .hasVoted(false)
                        .isActive(true)
                        .registrationDate(LocalDateTime.now())
                        .build());

                voterRepository.save(Voter.builder()
                        .cin("IJ567890")
                        .firstName("Omar")
                        .lastName("Tazi")
                        .email("omar.tazi@email.com")
                        .dateOfBirth(LocalDate.of(1995, 7, 18))
                        .address("654 Avenue Hassan II, Tanger")
                        .hasVoted(false)
                        .isActive(true)
                        .registrationDate(LocalDateTime.now())
                        .build());

                System.out.println("✅ " + voterRepository.count() + " électeurs initialisés");
                voterRepository.findAll().forEach(v ->
                        System.out.println("  - " + v.getCin() + ": " + v.getFirstName() + " " + v.getLastName())
                );
            } else {
                System.out.println("ℹ️ Des électeurs existent déjà (" + voterRepository.count() + ")");
            }
        };
    }
}