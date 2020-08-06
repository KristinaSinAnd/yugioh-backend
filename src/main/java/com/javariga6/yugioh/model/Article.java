package com.javariga6.yugioh.model;

import javax.persistence.*;

@Entity(name = "article")
public class Article {
    @Id
    @Column(name = "id_articles")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booster_set")
    private String boosterSet;

    @Column(name = "card_name")
    private String cardName;

    @Column
    private Edition edition;

    @Column
    private Rarity rarity;

    @Column
    private CardType cardType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoosterSet() {
        return boosterSet;
    }

    public void setBoosterSet(String boosterSet) {
        this.boosterSet = boosterSet;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", boosterSet='" + boosterSet + '\'' +
                ", cardName='" + cardName + '\'' +
                ", edition=" + edition +
                ", rarity=" + rarity +
                ", cardType=" + cardType +
                '}';
    }
}
