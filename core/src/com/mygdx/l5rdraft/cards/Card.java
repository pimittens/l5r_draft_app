package com.mygdx.l5rdraft.cards;

import java.util.List;

/**
 * a card
 */
public class Card {

    public enum RARITY {
        SUPER_COMMON(6),
        COMMON(3),
        PROVINCE(1),
        RARE(0);

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

    public Card(String name, RARITY rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public RARITY getRarity() {
        return rarity;
    }

    /*public static RARITY getRarityFromList(String cardName, List<Card> cardList) {
        for (Card c : cardList) {
            if (c.getName().equals(cardName)) {
                return c.getRarity();
            }
        }
        return RARITY.COMMON;
    }*/

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card c = (Card) obj;
            return this.name.equals(c.name) && this.rarity == c.rarity;
        }
        return false;
    }

    public Card copy() {
        return new Card(name, rarity);
    }
}
