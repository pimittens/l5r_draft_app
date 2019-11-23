package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.l5rdraft.Assets;

import java.util.ArrayList;
import java.util.List;

public class Pool {
    private List<Card> cards;
    private List<Integer> quantities;

    public Pool() {
        cards = new ArrayList<>();
        quantities = new ArrayList<>();
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
        return true;
    }

    public Card getCard(int pos) {
        return pos < cards.size() ? cards.get(pos) : cards.get(0);
    }

    public int getQuantity(int pos) {
        return pos < quantities.size() ? quantities.get(pos) : 0;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }
}
