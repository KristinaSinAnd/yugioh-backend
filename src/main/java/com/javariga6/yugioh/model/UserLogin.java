package com.javariga6.yugioh.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserLogin {
    @Email
    String email;
    @NotEmpty
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
