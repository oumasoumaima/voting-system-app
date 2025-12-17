package org.vote.voteservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.vote.voteservice.entities.Proposal;

import java.util.List;

@RepositoryRestResource(path = "proposals")
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByActive(Boolean active);
    List<Proposal> findByTitleContainingIgnoreCase(String title);
}