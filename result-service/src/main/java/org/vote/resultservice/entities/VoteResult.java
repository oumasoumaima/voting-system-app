package org.vote.resultservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vote_results")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String candidateName;

    @Column(nullable = false)
    private Long totalVotes;
}