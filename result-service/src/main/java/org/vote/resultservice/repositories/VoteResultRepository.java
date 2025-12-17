package org.vote.resultservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.vote.resultservice.entities.VoteResult;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "results")
public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

    // Trouver un résultat par nom de candidat
    Optional<VoteResult> findByCandidateName(String candidateName);

    // Récupérer tous les résultats triés par votes décroissants
    @Query("SELECT v FROM VoteResult v ORDER BY v.totalVotes DESC")
    List<VoteResult> findAllOrderByVotesDesc();

    // Alternative sans @Query (Spring Data génère automatiquement)
    // List<VoteResult> findAllByOrderByTotalVotesDesc();
}