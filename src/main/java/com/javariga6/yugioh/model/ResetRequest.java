package com.javariga6.yugioh.model;

import javax.validation.constraints.NotEmpty;

public class ResetRequest {
    @NotEmpty
    private String password;
    @NotEmpty
    private String tokenStr;

    @Override
    public String toString() {
        return "ResetRequest{" +
                "password='" + password + '\'' +
                ", tokenStr='" + tokenStr + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
    }
}
