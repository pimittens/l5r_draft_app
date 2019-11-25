package com.mygdx.l5rdraft.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * a pack of cards
 * basically just a List<Card> wrapper now lol
 */
public class Pack {

    private List<Card> cards;

    public Pack() {
        cards = new ArrayList<>();
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public Card getCard(int pos) {
        return pos < cards.size() ? cards.get(pos) : cards.get(0);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card popCard(int pos) {
        Card ret = cards.remove(pos);
        return ret;
    }

    public int size() {
        return cards.size();
    }
}
