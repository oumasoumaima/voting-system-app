package org.vote.resultservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vote.resultservice.entities.VoteResult;
import org.vote.resultservice.service.ResultCalculationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/results")
@CrossOrigin(origins = "http://localhost:3001")
@RequiredArgsConstructor
public class ResultController {

    private final ResultCalculationService resultCalculationService;

    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Object>> calculateResults() {
        try {
            List<VoteResult> results = resultCalculationService.calculateAndSaveResults();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Résultats calculés avec succès");
            response.put("results", results);
            response.put("totalCandidates", results.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<List<VoteResult>> getCurrentResults() {
        List<VoteResult> results = resultCalculationService.getCurrentResults();
        return ResponseEntity.ok(results);
    }

    @PostMapping("/increment/{candidateName}")
    public ResponseEntity<Map<String, Object>> incrementVote(@PathVariable String candidateName) {
        try {
            resultCalculationService.incrementVoteForCandidate(candidateName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Vote incrémenté pour " + candidateName);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}