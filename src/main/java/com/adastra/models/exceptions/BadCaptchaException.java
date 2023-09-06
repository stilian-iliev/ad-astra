package com.adastra.models.exceptions;

import org.springframework.security.core.AuthenticationException;

public class BadCaptchaException extends AuthenticationException {
    public BadCaptchaException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BadCaptchaException(String msg) {
        super(msg);
    }
}
