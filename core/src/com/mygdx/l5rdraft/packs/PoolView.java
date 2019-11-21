package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;

/**
 * a view that displays the pool of cards the user has picked
 */
public class PoolView {

    private float buffer = 25f;

    private Pack pool;

    private Rectangle dimen;

    private GlyphLayout layout;

    public PoolView(Rectangle dimen) {
        pool = new Pack();
        this.dimen = dimen;
        layout = new GlyphLayout();
    }

    public void addCardToPool(Card c, Texture t) {
        pool.addCard(c, t);
    }

    public Rectangle getDimen() {
        return dimen;
    }

    // todo: scrolling

    public void render(SpriteBatch batch, BitmapFont font) {
        //font.draw(batch, "test", 100, 100);
        float startX = dimen.x + buffer, nextY = dimen.y + dimen.height - buffer;
        String cardName;
        for (Card c : pool.getCards()) {
            cardName = c.getName().replace("_", " ");
            layout.setText(font, cardName);
            font.draw(batch, cardName, startX, nextY);
            nextY -= layout.height * 2 + buffer;
        }
    }

    public void resize(int width, int height) {
        dimen = new Rectangle( width * 0.8f, 10, width * 0.2f - 10, height - 20);
    }
}
