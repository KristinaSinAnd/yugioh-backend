package com.javariga6.yugioh.model;

import javax.persistence.*;

@Entity(name = "card_storage")
public class CardStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card_storage")
    private Long id;

    @Column(name = "storage_name")
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
