package com.javariga6.yugioh.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "stock_items")
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock_items")
    private Long id;

    @Column(name = "card_condition")
    private CardCondition cardCondition;

    @Column(name = "card_value")
    private BigDecimal cardValue;

    @Column(name = "card_value_when_sold")
    private BigDecimal cardValueWhenSold;

    @Column(name = "in_shop")
    private Boolean inShop;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_card_storage")
    private CardStorage cardStorage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_article")
    private Article article;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public CardCondition getCardCondition() { return cardCondition; }

    public void setCardCondition(CardCondition cardCondition) { this.cardCondition = cardCondition; }

    public BigDecimal getCardValue() { return cardValue; }

    public void setCardValue(BigDecimal cardValue) { this.cardValue = cardValue; }

    public boolean isInShop() { return inShop; }

    public BigDecimal getCardValueWhenSold() {
        return cardValueWhenSold;
    }

    public void setCardValueWhenSold(BigDecimal cardValueWhenSold) {
        this.cardValueWhenSold = cardValueWhenSold;
    }

    public Boolean getInShop() {
        return inShop;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setInShop(Boolean inShop) {
        this.inShop = inShop;
    }



    public CardStorage getCardStorage() { return cardStorage; }

    public void setCardStorage(CardStorage cardStorage) { this.cardStorage = cardStorage; }

    public Article getArticle() { return article; }

    public void setArticle(Article article) { this.article = article; }

    @Override
    public String toString() {
        return "StockItem{" +
                "id=" + id +
                ", cardCondition=" + cardCondition +
                ", cardValue=" + cardValue +
                ", cardValueWhenSold=" + cardValueWhenSold +
                ", inShop=" + inShop +
                ", comments='" + comments + '\'' +
                ", cardStorage=" + cardStorage +
                ", article=" + article +
                '}';
    }
}
