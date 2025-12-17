package org.vote.resultservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private Long id;
    private String voterCin;
    private Long candidateId;
    private String candidateName;
    private Long proposalId;
    private String proposalTitle;
    private LocalDateTime voteDate;
}