package org.vote.voteservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.vote.voteservice.entities.Vote;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "votes")
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByProposalId(Long proposalId);
    List<Vote> findByCandidateId(Long candidateId);
    Optional<Vote> findByVoterCinAndProposalId(String voterCin, Long proposalId);
    boolean existsByVoterCinAndProposalId(String voterCin, Long proposalId);
}