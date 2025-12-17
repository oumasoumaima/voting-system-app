package org.vote.voteservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String voterCin;

    @Column(nullable = false)
    private Long candidateId;

    @Column(nullable = false)
    private String candidateName;  // ✅ AJOUTÉ

    @Column(nullable = false)
    private Long proposalId;

    @Column(nullable = false)
    private String proposalTitle;  // ✅ AJOUTÉ

    @Column(nullable = false)
    private LocalDateTime voteDate;  // ✅ Renommé de voteTime à voteDate

    @PrePersist
    protected void onCreate() {
        if (voteDate == null) {
            voteDate = LocalDateTime.now();
        }
    }
}