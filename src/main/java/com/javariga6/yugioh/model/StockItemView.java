package com.javariga6.yugioh.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "v_stock_item")
public class StockItemView {
    @Id
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

    @Column(name = "id_articles")
    private Long articleId;

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

    @Column(name = "id")
    private Long storageId;

    @Column
    private String storageName;

    public Long getId() {
        return id;
    }

    public CardCondition getCardCondition() {
        return cardCondition;
    }

    public BigDecimal getCardValue() {
        return cardValue;
    }

    public BigDecimal getCardValueWhenSold() {
        return cardValueWhenSold;
    }

    public Boolean getInShop() {
        return inShop;
    }

    public String getComments() {
        return comments;
    }

    public Long getArticleId() {
        return articleId;
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

    public Long getStorageId() {
        return storageId;
    }

    public String getStorageName() {
        return storageName;
    }
}
