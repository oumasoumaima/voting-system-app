package org.vote.resultservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    private Long id;
    private String name;
    private String party;
    private String program;
}