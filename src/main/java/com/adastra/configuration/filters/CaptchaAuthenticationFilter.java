package com.adastra.configuration.filters;

import com.adastra.configuration.CaptchaConfig;
import com.adastra.configuration.SecurityConfig;
import com.adastra.models.exceptions.BadCaptchaException;
import com.adastra.services.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaService captchaService;

    private final ForwardAuthenticationFailureHandler failureHandler;

    public CaptchaAuthenticationFilter() {
        this.failureHandler = new ForwardAuthenticationFailureHandler(SecurityConfig.LOGIN_FAIL_URL);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(SecurityConfig.LOGIN_URL) && request.getMethod().equals("POST")){
            String clientToken = request.getParameter(CaptchaConfig.CLIENT_TOKEN_PARAMETER);

            if (!captchaService.isValid(clientToken)) {
                String errorMessage = "CAPTCHA validation failed";
                failureHandler.onAuthenticationFailure(request, response, new BadCaptchaException(errorMessage));

                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
