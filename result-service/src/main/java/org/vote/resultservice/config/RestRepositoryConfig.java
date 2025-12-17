package org.vote.resultservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.vote.resultservice.entities.VoteResult;

@Configuration
public class RestRepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config,
            CorsRegistry cors) {

        // Exposer les IDs
        config.exposeIdsFor(VoteResult.class);

        // Activer toutes les méthodes HTTP
        config.getExposureConfiguration()
                .forDomainType(VoteResult.class)
                .withItemExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.PUT,
                                HttpMethod.PATCH, HttpMethod.DELETE))
                .withCollectionExposure((metadata, httpMethods) ->
                        httpMethods.enable(HttpMethod.GET, HttpMethod.POST));

        // Configuration CORS
        cors.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*");
    }
}