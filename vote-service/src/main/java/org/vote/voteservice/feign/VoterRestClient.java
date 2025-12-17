package org.vote.voteservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.vote.voteservice.model.Voter;

@FeignClient(name = "voter-service", url = "http://localhost:8081")
public interface VoterRestClient {

    @GetMapping("/voters/search/findByCin?cin={cin}")
    EntityModel<Voter> getVoterByCin(@PathVariable("cin") String cin);

    // AJOUTEZ CETTE MÉTHODE pour mettre à jour l'électeur
    @PatchMapping("/voters/{id}")
    EntityModel<Voter> updateVoter(@PathVariable("id") Long id, @RequestBody Voter voter);
}