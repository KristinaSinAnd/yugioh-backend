package com.javariga6.yugioh.model;

import javax.persistence.*;

@Entity
public class CardStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String storageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    @Override
    public String toString() {
        return "CardStorage{" +
                "id=" + id +
                ", storageName='" + storageName + '\'' +
                '}';
    }
}
