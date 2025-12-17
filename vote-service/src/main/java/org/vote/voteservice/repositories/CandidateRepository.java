package org.vote.voteservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.vote.voteservice.entities.Candidate;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "candidates")
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // Recherche par parti
    List<Candidate> findByParty(String party);

    // Recherche par nom (insensible à la casse)
    List<Candidate> findByNameContainingIgnoreCase(String name);

    // Recherche par nom exact
    Optional<Candidate> findByName(String name);
}