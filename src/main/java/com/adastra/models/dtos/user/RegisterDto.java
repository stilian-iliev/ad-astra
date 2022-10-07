package com.adastra.models.dtos.user;

import com.adastra.validation.MatchingFields;
import com.adastra.validation.UniqueUsername;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@MatchingFields(first = "password", second = "confirmPassword")
public class RegisterDto {
    @NotBlank
    @UniqueUsername
    private String username;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;

    private String confirmPassword;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
