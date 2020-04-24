package com.hsbc.exchangerate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Profile("rest-template-test")
@Configuration
public class RestTemplateTestConfiguration {
    @Bean(name = "ratesApiRestTestTemplate")
    @Primary
    public RestTemplate ratesApiRestTemplate() {
        return new RestTemplate();
    }
}
