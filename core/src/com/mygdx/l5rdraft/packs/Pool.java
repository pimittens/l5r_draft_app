package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.l5rdraft.Assets;

import java.util.ArrayList;
import java.util.List;

public class Pool {
    private List<Card> cards;
    private List<Texture> cardImages;
    private List<Integer> quantities;

    public Pool() {
        cards = new ArrayList<>();
        cardImages = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    public void addCard(Card c, Assets assets) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equals(c)) {
                quantities.set(i, quantities.get(i) + 1);
                return;
            }
        }
        cardImages.add(assets.get(c.getName()));
        cards.add(c);
        quantities.add(1);
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

    /*public Card popCard(int pos) {
        Card ret = cards.remove(pos);
        cardImages.remove(pos);
        quantities.remove(pos);
        return ret;
    }*/

    public Texture getCardImage(int pos) {
        return pos < cardImages.size() ? cardImages.get(pos) : cardImages.get(0);
    }

    public int size() {
        return cards.size();
    }
}
