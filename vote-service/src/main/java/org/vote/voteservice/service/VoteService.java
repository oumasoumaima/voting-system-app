package org.vote.voteservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.vote.voteservice.entities.Candidate;
import org.vote.voteservice.entities.Proposal;
import org.vote.voteservice.entities.Vote;
import org.vote.voteservice.repositories.CandidateRepository;
import org.vote.voteservice.repositories.ProposalRepository;
import org.vote.voteservice.repositories.VoteRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;
    private final ProposalRepository proposalRepository;
    private final RestTemplate restTemplate;

    private static final String VOTER_SERVICE_URL = "http://localhost:8081";
    private static final String RESULT_SERVICE_URL = "http://localhost:8083";

    @Transactional
    public Vote submitVote(String voterCin, Long candidateId, Long proposalId) {
        log.info("🗳️ Traitement du vote pour CIN: {}, Candidat: {}, Proposition: {}",
                voterCin, candidateId, proposalId);

        // 1. Vérifier l'électeur
        log.info("1️⃣ Vérification de l'électeur...");
        Map<String, Object> voter = verifyVoter(voterCin);
        log.info("✅ Électeur vérifié: {} {}", voter.get("firstName"), voter.get("lastName"));

        // 2. Vérifier le candidat
        log.info("2️⃣ Vérification du candidat...");
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidat non trouvé avec l'ID: " + candidateId));

        // ✅ DÉBOGAGE : Afficher le candidat complet
        log.info("✅ Candidat trouvé: {}", candidate);
        log.info("✅ Nom du candidat: {}", candidate.getName());

        // 3. Vérifier la proposition
        log.info("3️⃣ Vérification de la proposition...");
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new IllegalArgumentException("Proposition non trouvée avec l'ID: " + proposalId));

        if (!proposal.getActive()) {
            throw new IllegalStateException("La proposition n'est pas active");
        }
        log.info("✅ Proposition active: {}", proposal.getTitle());

        // 4. Enregistrer le vote
        log.info("4️⃣ Enregistrement du vote...");
        Vote vote = Vote.builder()
                .voterCin(voterCin)
                .candidateId(candidateId)
                .candidateName(candidate.getName())  // ✅ Utilise getName()
                .proposalId(proposalId)
                .proposalTitle(proposal.getTitle())
                .voteDate(LocalDateTime.now())
                .build();

        vote = voteRepository.save(vote);
        log.info("✅ Vote enregistré avec ID: {}", vote.getId());

        // 5. Marquer l'électeur comme ayant voté
        log.info("5️⃣ Mise à jour du statut de l'électeur...");
        markVoterAsVoted(voterCin);
        log.info("✅ Électeur marqué comme ayant voté");

        // 6. Mettre à jour les résultats
        log.info("6️⃣ Mise à jour des résultats...");
        updateResults(candidate.getName());  // ✅ Utilise getName()
        log.info("✅ Résultats mis à jour");

        log.info("🎉 Vote traité avec succès !");
        return vote;
    }

    private Map<String, Object> verifyVoter(String cin) {
        try {
            String url = VOTER_SERVICE_URL + "/voters/search/findByCin?cin=" + cin;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                throw new IllegalArgumentException("Électeur non trouvé avec le CIN: " + cin);
            }

            Boolean hasVoted = (Boolean) response.get("hasVoted");
            Boolean isActive = (Boolean) response.get("isActive");

            if (hasVoted != null && hasVoted) {
                throw new IllegalStateException("Cet électeur a déjà voté");
            }

            if (isActive != null && !isActive) {
                throw new IllegalStateException("Ce compte électeur n'est pas actif");
            }

            return response;
        } catch (Exception e) {
            log.error("Erreur lors de la vérification de l'électeur: {}", e.getMessage());
            throw new IllegalArgumentException("Impossible de vérifier l'électeur: " + e.getMessage());
        }
    }

    private void markVoterAsVoted(String cin) {
        try {
            String url = VOTER_SERVICE_URL + "/voters/search/findByCin?cin=" + cin;
            Map<String, Object> voter = restTemplate.getForObject(url, Map.class);

            if (voter != null) {
                String voterUrl = ((Map<String, Map<String, String>>) voter.get("_links"))
                        .get("self")
                        .get("href");

                Map<String, Object> updateData = new HashMap<>();
                updateData.put("hasVoted", true);

                restTemplate.patchForObject(voterUrl, updateData, Map.class);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'électeur: {}", e.getMessage());
        }
    }

    private void updateResults(String candidateName) {
        try {
            String url = RESULT_SERVICE_URL + "/results/increment/" + candidateName;

            Map<String, Object> response = restTemplate.postForObject(url, null, Map.class);

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                log.info("✅ Résultat mis à jour pour {}", candidateName);
            }
        } catch (Exception e) {
            log.error("❌ Erreur lors de la mise à jour des résultats: {}", e.getMessage());
            // Ne pas propager l'erreur pour ne pas bloquer le vote
        }
    }
}