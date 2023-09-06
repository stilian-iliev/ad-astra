package com.adastra.configuration;

import com.adastra.configuration.filters.CaptchaAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CaptchaConfig {
    public static final String CLIENT_TOKEN_PARAMETER = "g-recaptcha-response";

    @Bean
    public CaptchaAuthenticationFilter captchaAuthenticationFilter() {
        return new CaptchaAuthenticationFilter();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
