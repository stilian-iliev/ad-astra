package com.adastra.services;

import com.adastra.models.dtos.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CaptchaService {
    @Autowired
    private RestTemplate restTemplate;

    public boolean validate(HttpServletRequest request) throws ServletException, IOException {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String secret = "6Ld-19AnAAAAAM6O4LYfL_sDN_PG8NG9b2qJ7bk2";
        String recap = request.getParameter("g-recaptcha-response");

        String params = "?secret=" + secret + "&response=" + recap;
        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url+params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();

        if (reCaptchaResponse.isSuccess()) {
            System.out.println("holy shit first tryy");
            return true;
        } else {
            System.out.println("damn thats sucks");
            return false;
        }
    }
}
