package org.vote.voteservice.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vote.voteservice.entities.Vote;
import org.vote.voteservice.service.VoteService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/votes")
@CrossOrigin(origins = "http://localhost:3001") // IMPORTANT pour CORS
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitVote(@RequestBody VoteRequest request) {
        try {
            System.out.println("📥 Requête de vote reçue:");
            System.out.println("   CIN: " + request.getVoterCin());
            System.out.println("   Candidat ID: " + request.getCandidateId());
            System.out.println("   Proposition ID: " + request.getProposalId());

            Vote vote = voteService.submitVote(
                    request.getVoterCin(),
                    request.getCandidateId(),
                    request.getProposalId()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Vote enregistré avec succès");
            response.put("vote", vote);

            System.out.println("✅ Vote traité avec succès");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Erreur de validation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));

        } catch (IllegalStateException e) {
            System.err.println("❌ Erreur d'état: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(e.getMessage()));

        } catch (Exception e) {
            System.err.println("❌ Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Service de vote opérationnel ✅");
        return ResponseEntity.ok(response);
    }

    @Data
    public static class VoteRequest {
        private String voterCin;
        private Long candidateId;
        private Long proposalId;
    }

    @Data
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}