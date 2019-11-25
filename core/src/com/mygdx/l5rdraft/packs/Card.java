package com.mygdx.l5rdraft.packs;

import java.util.List;

/**
 * a card
 */
public class Card {

    public enum RARITY {
        SUPER_COMMON(6),
        COMMON(3),
        PROVINCE(1),
        RARE(0)

        int cardCount;
        RARITY(int count) {
            cardCount = count;
        }
        int getCardCount() {
            return cardCount;
        }
    }

    private String name;
    private RARITY rarity;
    private String id;

    public Card(String name, RARITY rarity, String id) {
        this.name = name;
        this.rarity = rarity;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RARITY getRarity() {
        return rarity;
    }

    public static RARITY getRarityFromList(String cardName, List<Card> cardList) {
        for (Card c : cardList) {
            if (c.getName().equals(cardName)) {
                return c.getRarity();
            }
        }
        return RARITY.COMMON;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card c = (Card) obj;
            return this.name.equals(c.name) && this.rarity == c.rarity;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + id.hashCode();
        hash = 31 * hash + name.hashCode();
        return hash;
    }

    public Card copy() {
        return new Card(name, rarity, id);
    }

    public class Builder {
        private final String name;
        private final RARITY rarity;
        private String id;

        public Builder() {
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder rarity(RARITY rarity) {
            this.rarity = rarity;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Card build() {
            return new Card(name, rarity, id);
        }
    }
}
