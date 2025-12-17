package org.vote.resultservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.vote.resultservice.entities.VoteResult;
import org.vote.resultservice.model.Candidate;
import org.vote.resultservice.model.Vote;
import org.vote.resultservice.repositories.VoteResultRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResultCalculationService {

    private final VoteResultRepository voteResultRepository;
    private final RestTemplate restTemplate;  // ⬅️ Injection via constructeur

    private static final String VOTE_SERVICE_URL = "http://localhost:8082";
    private static final String CANDIDATE_SERVICE_URL = "http://localhost:8082"; // ✅ Correct si les candidats sont dans vote-service

    @Transactional
    public List<VoteResult> calculateAndSaveResults() {
        System.out.println("🔄 Calcul des résultats en cours...");

        try {
            // 1. Récupérer tous les votes via REST
            List<Vote> allVotes = getAllVotes();
            System.out.println("📊 Nombre total de votes : " + allVotes.size());

            // 2. Compter les votes par candidat
            Map<Long, Long> voteCounts = new HashMap<>();
            for (Vote vote : allVotes) {
                voteCounts.merge(vote.getCandidateId(), 1L, Long::sum);
            }

            // 3. Récupérer tous les candidats via REST
            List<Candidate> candidates = getAllCandidates();
            System.out.println("👥 Nombre de candidats : " + candidates.size());

            // 4. Mettre à jour les résultats
            voteResultRepository.deleteAll();

            for (Candidate candidate : candidates) {
                Long voteCount = voteCounts.getOrDefault(candidate.getId(), 0L);

                VoteResult result = VoteResult.builder()
                        .candidateName(candidate.getName())
                        .totalVotes(voteCount)
                        .build();

                voteResultRepository.save(result);
                System.out.println("✅ " + candidate.getName() + " : " + voteCount + " votes");
            }

            return voteResultRepository.findAllOrderByVotesDesc();

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du calcul des résultats: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Impossible de calculer les résultats", e);
        }
    }

    private List<Vote> getAllVotes() {
        try {
            String url = VOTE_SERVICE_URL + "/votes";

            ResponseEntity<CollectionModel<EntityModel<Vote>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CollectionModel<EntityModel<Vote>>>() {}
            );

            if (response.getBody() != null && response.getBody().getContent() != null) {
                List<Vote> votes = new ArrayList<>();
                for (EntityModel<Vote> entityModel : response.getBody().getContent()) {
                    if (entityModel.getContent() != null) {
                        votes.add(entityModel.getContent());
                    }
                }
                return votes;
            }
            return Collections.emptyList();

        } catch (Exception e) {
            System.err.println("⚠️ Erreur lors de la récupération des votes: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<Candidate> getAllCandidates() {
        try {
            String url = CANDIDATE_SERVICE_URL + "/candidates";

            ResponseEntity<CollectionModel<EntityModel<Candidate>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CollectionModel<EntityModel<Candidate>>>() {}
            );

            if (response.getBody() != null && response.getBody().getContent() != null) {
                List<Candidate> candidates = new ArrayList<>();
                for (EntityModel<Candidate> entityModel : response.getBody().getContent()) {
                    if (entityModel.getContent() != null) {
                        candidates.add(entityModel.getContent());
                    }
                }
                return candidates;
            }
            return Collections.emptyList();

        } catch (Exception e) {
            System.err.println("⚠️ Erreur lors de la récupération des candidats: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<VoteResult> getCurrentResults() {
        return voteResultRepository.findAllOrderByVotesDesc();
    }

    @Transactional
    public void incrementVoteForCandidate(String candidateName) {
        try {
            Optional<VoteResult> existingResult = voteResultRepository.findByCandidateName(candidateName);

            if (existingResult.isPresent()) {
                VoteResult result = existingResult.get();
                result.setTotalVotes(result.getTotalVotes() + 1);
                voteResultRepository.save(result);
                System.out.println("✅ Vote incrémenté pour " + candidateName + ": " + result.getTotalVotes() + " votes");
            } else {
                // Si le résultat n'existe pas, le créer
                VoteResult newResult = VoteResult.builder()
                        .candidateName(candidateName)
                        .totalVotes(1L)
                        .build();
                voteResultRepository.save(newResult);
                System.out.println("✅ Nouveau résultat créé pour " + candidateName + ": 1 vote");
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'incrémentation: " + e.getMessage());
            throw new RuntimeException("Impossible d'incrémenter les votes", e);
        }
    }
}