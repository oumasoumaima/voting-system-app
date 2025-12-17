package com.example.authentication.voterservice.repository;

import com.example.authentication.voterservice.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "voters", path = "voters")
public interface VoterRepository extends JpaRepository<Voter, Long> {
    Optional<Voter> findByCin(String cin);
}