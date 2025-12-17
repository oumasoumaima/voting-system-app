package org.vote.voteservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.vote.voteservice.entities.Candidate;
import org.vote.voteservice.entities.Proposal;
import org.vote.voteservice.entities.Vote;

@Configuration
public class RestRepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config,
            CorsRegistry cors) {

        // Exposer les IDs dans les réponses JSON
        config.exposeIdsFor(Vote.class, Candidate.class, Proposal.class);

        // Activer toutes les méthodes HTTP pour toutes les entités
        config.getExposureConfiguration()
                .forDomainType(Vote.class)
                .withItemExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE))
                .withCollectionExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.POST));

        config.getExposureConfiguration()
                .forDomainType(Candidate.class)
                .withItemExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE))
                .withCollectionExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.POST));

        config.getExposureConfiguration()
                .forDomainType(Proposal.class)
                .withItemExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE))
                .withCollectionExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.POST));

        // Configuration CORS
        cors.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*");
    }
}