package com.adastra.services;

import com.adastra.models.dtos.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.captcha.secretKey}")
    private String secret;

    @Value("${spring.captcha.verifyUrl}")
    private String url;

    public boolean isValid(String clientResponseToken) {
        String params = "?secret=" + secret + "&response=" + clientResponseToken;
        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url+params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();

        return reCaptchaResponse != null && reCaptchaResponse.isSuccess();
    }
}
