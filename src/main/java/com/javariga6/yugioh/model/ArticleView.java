package com.javariga6.yugioh.model;

import javax.persistence.*;


@Entity(name = "v_article")
public class ArticleView {

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

    @Column(name = "card_count")
    private String cardCount;

    public Long getId() {
        return id;
    }

    public String getBoosterSet() {
        return boosterSet;
    }

    public String getCardName() {
        return cardName;
    }

    public Edition getEdition() {
        return edition;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getCardCount() {
        return cardCount;
    }

    @Override
    public String toString() {
        return "ArticleView{" +
                "id=" + id +
                ", boosterSet='" + boosterSet + '\'' +
                ", cardName='" + cardName + '\'' +
                ", edition=" + edition +
                ", rarity=" + rarity +
                ", cardType=" + cardType +
                ", cardCount='" + cardCount + '\'' +
                '}';
    }
}
