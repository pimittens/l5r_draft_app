package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * a view that displays the pool of cards the user has picked
 */
public class PoolView {

    private float buffer = 25f;

    private Pool pool;
    private List<Texture> cardImages;

    private Rectangle dimen;

    private GlyphLayout layout;

    public PoolView(Rectangle dimen) {
        pool = new Pool();
        cardImages = new ArrayList<>();
        this.dimen = dimen;
        layout = new GlyphLayout();
    }

    public void updatePool(Pool p) {
        pool = p;
    }

    public Rectangle getDimen() {
        return dimen;
    }

    // todo: scrolling

    public void render(SpriteBatch batch, BitmapFont font) {
        if (pool == null) {
            return;
        }
        float startX = dimen.x + buffer, nextY = dimen.y + dimen.height - buffer;
        String cardName;
        for (int i = 0; i < pool.size(); i++) {
            cardName = pool.getCard(i).getName().replace("_", " ");
            layout.setText(font, cardName);
            font.draw(batch, String.format("%s x%s", cardName, pool.getQuantity(i)), startX, nextY);
            nextY -= layout.height * 2 + buffer;
        }
    }

    public void resize(int width, int height) {
        dimen = new Rectangle( width * 0.8f, 10, width * 0.2f - 10, height * 0.95f - 20);
    }
}
