package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * a pack of cards
 * also used to represent a card pool
 */
public class Pack {

    private List<Card> cards;
    private List<Texture> cardImages;

    public Pack() {
        cards = new ArrayList<>();
        cardImages = new ArrayList<>();
    }

    public void addCard(Card c, Texture t) {
        cards.add(c);
        cardImages.add(t);
    }

    public Card getCard(int pos) {
        return pos < cards.size() ? cards.get(pos) : cards.get(0);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card popCard(int pos) {
        Card ret = cards.remove(pos);
        cardImages.remove(pos);
        return ret;
    }

    public Texture getCardImage(int pos) {
        return pos < cardImages.size() ? cardImages.get(pos) : cardImages.get(0);
    }

    public int size() {
        return cards.size();
    }
}
