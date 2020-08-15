package com.javariga6.yugioh.model;

import javax.persistence.*;

@Entity
public class PassRecoveryToken {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PassRecoveryToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
