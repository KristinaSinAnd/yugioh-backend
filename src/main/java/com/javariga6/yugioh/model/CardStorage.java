package com.javariga6.yugioh.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class CardStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @NotBlank
    private String storageName;

    public CardStorage() {
    }

    public CardStorage(String storageName) {
        this.storageName = storageName;
    }

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
