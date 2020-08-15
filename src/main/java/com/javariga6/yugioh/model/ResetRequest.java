package com.javariga6.yugioh.model;

public class ResetRequest {
    private String password;
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
