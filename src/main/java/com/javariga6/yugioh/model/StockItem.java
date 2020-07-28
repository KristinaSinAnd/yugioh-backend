package com.javariga6.yugioh.model;

import javax.persistence.*;

@Entity(name = "stock_items")
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock_items")
    private Long id;

    @Column(name = "card_condition")
    private  CardCondition cardCondition;

    @Column(name = "card_value")
    private String cardValue;

    @Column(name = "in_shop")
    private boolean inShop;

    @ManyToOne
    @JoinColumn(name = "id_card_storage")
    private CardStorage cardStorage;

    @ManyToOne
    @JoinColumn(name = "id_articles")
    private Articles articles;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCardCondition() { return cardCondition; }

    public void setCardCondition(String cardCondition) { this.cardCondition = cardCondition; }

    public String getCardValue() { return cardValue; }

    public void setCardValue(String cardValue) { this.cardValue = cardValue; }

    public boolean isInShop() { return inShop; }

    public void setInShop(boolean inShop) { this.inShop = inShop; }

    public CardStorage getCardStorage() { return cardStorage; }

    public void setCardStorage(CardStorage cardStorage) { this.cardStorage = cardStorage; }

    public Articles getArticles() { return articles; }

    public void setArticles(Articles articles) { this.articles = articles; }

    @Override
    public String toString() {
        return "StockItem{" +
                "id=" + id +
                ", cardCondition='" + cardCondition + '\'' +
                ", cardValue='" + cardValue + '\'' +
                ", inShop=" + inShop +
                ", cardStorage=" + cardStorage +
                ", articles=" + articles +
                '}';
    }
}
