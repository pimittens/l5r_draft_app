package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.cards.Card;
import com.mygdx.l5rdraft.cards.Pool;

import java.util.ArrayList;
import java.util.List;

public class DeckView extends PoolView {

    public DeckView(Rectangle dimen) {
        super(dimen);
    }

    public void click() {
        // todo: remove cards on click and add them back to the pool
    }

    public void addCard(Card c) {
        getPool().addCard(c);
    }
}
