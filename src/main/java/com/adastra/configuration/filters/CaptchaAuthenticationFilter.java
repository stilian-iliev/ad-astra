package com.adastra.configuration.filters;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/login") && request.getMethod().equals("POST")){
            if (request.getParameter("g-recaptcha-response").equals("")) {
                String errorMessage = "CAPTCHA validation failed"; // Customize the error message as needed
                String redirectUrl = "/login-error";

                request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new BadCredentialsException(errorMessage));
                request.getRequestDispatcher(redirectUrl).forward(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
