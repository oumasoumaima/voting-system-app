package com.example.authentication.voterservice.config;

import com.example.authentication.voterservice.model.Voter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestRepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config,
            CorsRegistry cors) {

        // Exposer les IDs dans les réponses JSON
        config.exposeIdsFor(Voter.class);

        // Activer toutes les méthodes HTTP (GET, POST, PUT, DELETE, PATCH)
        config.getExposureConfiguration()
                .forDomainType(Voter.class)
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