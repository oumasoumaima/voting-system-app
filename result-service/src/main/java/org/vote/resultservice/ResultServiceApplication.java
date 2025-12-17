package org.vote.resultservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.vote.resultservice.entities.VoteResult;
import org.vote.resultservice.repositories.VoteResultRepository;

@SpringBootApplication
public class ResultServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResultServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner initVoteResults(VoteResultRepository repository) {
        return args -> {
            // ✅ Vérifier si des résultats existent déjà
            if (repository.count() == 0) {
                System.out.println("🔄 Initialisation des résultats de vote...");

                repository.save(VoteResult.builder()
                        .candidateName("Mohammed Alami")
                        .totalVotes(0L)
                        .build());

                repository.save(VoteResult.builder()
                        .candidateName("Fatima Zahra Bennani")
                        .totalVotes(0L)
                        .build());

                repository.save(VoteResult.builder()
                        .candidateName("Youssef El Fassi")
                        .totalVotes(0L)
                        .build());

                repository.save(VoteResult.builder()
                        .candidateName("Khadija Idrissi")
                        .totalVotes(0L)
                        .build());

                System.out.println("✅ " + repository.count() + " résultats initialisés");
            } else {
                System.out.println("ℹ️ Des résultats existent déjà (" + repository.count() + ")");
                // Afficher les résultats existants
                repository.findAll().forEach(r ->
                        System.out.println("  - " + r.getCandidateName() + ": " + r.getTotalVotes() + " votes")
                );
            }
        };
    }
}