package com.epam.brest.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;

@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(
            @Value("${rest.server:#{'http://localhost:8088'}}") final String restServerEndpoint) {
        return restTemplate -> {
            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(restServerEndpoint));
            restTemplate.getInterceptors()
                    .add((request, body, execution) -> {

                        final HttpHeaders headers = request.getHeaders();
                        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

                        return execution.execute(request, body);
                    });
            restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
            // etc.
        };
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder(final RestTemplateCustomizer restTemplateCustomizer) {
        return new RestTemplateBuilder(restTemplateCustomizer);
    }
}
