package org.vote.voteservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.vote.voteservice.entities.Candidate;
import org.vote.voteservice.entities.Proposal;
import org.vote.voteservice.repositories.CandidateRepository;
import org.vote.voteservice.repositories.ProposalRepository;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
public class VoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner initData(CandidateRepository candidateRepository,
                                      ProposalRepository proposalRepository) {
        return args -> {
            // ✅ VÉRIFIER SI DES DONNÉES EXISTENT DÉJÀ
            if (candidateRepository.count() == 0) {
                System.out.println("🔄 Initialisation des candidats...");

                candidateRepository.save(Candidate.builder()
                        .name("Mohammed Alami")
                        .party("Parti Démocratique")
                        .program("Programme axé sur l'éducation et la santé")
                        .build());

                candidateRepository.save(Candidate.builder()
                        .name("Fatima Zahra Bennani")
                        .party("Parti Progressiste")
                        .program("Programme de développement économique")
                        .build());

                candidateRepository.save(Candidate.builder()
                        .name("Youssef El Fassi")
                        .party("Parti Social")
                        .program("Programme de justice sociale")
                        .build());

                candidateRepository.save(Candidate.builder()
                        .name("Khadija Idrissi")
                        .party("Parti Écologiste")
                        .program("Programme environnemental")
                        .build());

                System.out.println("✅ " + candidateRepository.count() + " candidats initialisés");
            } else {
                System.out.println("ℹ️ Les candidats existent déjà (" + candidateRepository.count() + ")");
            }

            if (proposalRepository.count() == 0) {
                System.out.println("🔄 Initialisation des propositions...");

                // Proposition 1 - Élection Présidentielle
                proposalRepository.save(Proposal.builder()
                        .title("Élection Présidentielle 2025")
                        .description("Élection pour le nouveau président de la République")
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(30))
                        .active(true)
                        .build());

                // Proposition 2 - Référendum
                proposalRepository.save(Proposal.builder()
                        .title("Référendum sur la réforme constitutionnelle")
                        .description("Vote sur les amendements constitutionnels proposés")
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(15))
                        .active(true)
                        .build());

                // Proposition 3 - Élection Locale
                proposalRepository.save(Proposal.builder()
                        .title("Élection Municipale 2025")
                        .description("Élection des représentants municipaux")
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(20))
                        .active(false)  // Pas encore active
                        .build());

                // Proposition 4 - Vote Budget
                proposalRepository.save(Proposal.builder()
                        .title("Vote sur le budget 2025")
                        .description("Approbation du budget annuel de la commune")
                        .startDate(LocalDateTime.now().minusDays(5))
                        .endDate(LocalDateTime.now().plusDays(10))
                        .active(true)
                        .build());

                System.out.println("✅ " + proposalRepository.count() + " proposition(s) initialisée(s)");
            }
        };
    }
}