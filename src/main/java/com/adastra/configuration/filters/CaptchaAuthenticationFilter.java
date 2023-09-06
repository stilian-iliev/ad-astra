package com.adastra.configuration.filters;

import com.adastra.configuration.CaptchaConfig;
import com.adastra.configuration.SecurityConfig;
import com.adastra.services.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CaptchaService captchaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals(SecurityConfig.LOGIN_URL) && request.getMethod().equals("POST")){
            String clientToken = request.getParameter(CaptchaConfig.CLIENT_TOKEN_PARAMETER);

            if (!captchaService.isValid(clientToken)) {
                String errorMessage = "CAPTCHA validation failed";
                String redirectUrl = SecurityConfig.LOGIN_FAIL_URL;

                request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new BadCredentialsException(errorMessage));
                request.getRequestDispatcher(redirectUrl).forward(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
