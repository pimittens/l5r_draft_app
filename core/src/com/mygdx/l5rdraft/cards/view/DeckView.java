package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.cards.Pool;

import java.util.ArrayList;
import java.util.List;

public class DeckView {

    private Pool pool;
    private List<Texture> cardImages;
    private Rectangle dimen;

    public DeckView(Rectangle dimen) {
        pool = new Pool();
        cardImages = new ArrayList<>();
        this.dimen = dimen;
    }

    public Rectangle getDimen() {
        return dimen;
    }

    public void render(SpriteBatch batch) {

    }

    public void resize(int width, int height) {
        dimen.setPosition(width * 0.75f + 10, 10);
        dimen.setSize(width * 0.25f - 20, height - 20);
    }
}
