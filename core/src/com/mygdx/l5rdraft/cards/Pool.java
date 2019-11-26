package com.mygdx.l5rdraft.cards;

import java.util.ArrayList;
import java.util.List;

public class Pool {

    private List<Card> cards;
    private List<Integer> quantities, quantityInDeck;

    public Pool() {
        cards = new ArrayList<>();
        quantities = new ArrayList<>();
        quantityInDeck = new ArrayList<>();
    }

    /**
     * adds a card to the pool
     * if the card is already in the pool the quantity is updated instead
     * return true if the card was added to the pool or false if
     * the card was already in the pool and the quantity was updated
     *
     * @param c the card to add
     * @return true if the card was added, false if the quantity was updated
     */
    public boolean addCard(Card c) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equals(c)) {
                quantities.set(i, quantities.get(i) + 1);
                return false;
            }
        }
        cards.add(c);
        quantities.add(1);
        quantityInDeck.add(0);
        return true;
    }

    public void changeQuantityInDeck(int pos, int incr) {
        if (pos < quantityInDeck.size()) {
            quantityInDeck.set(pos, quantityInDeck.get(pos) + incr);
        }
    }

    public Card getCard(int pos) {
        return pos < cards.size() ? cards.get(pos) : cards.get(0);
    }

    public int getQuantity(int pos) {
        return pos < quantities.size() ? quantities.get(pos) : 0;
    }

    public int getQuantityInDeck(int pos) {
        return pos < quantityInDeck.size() ? quantityInDeck.get(pos) : 0;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }
}
