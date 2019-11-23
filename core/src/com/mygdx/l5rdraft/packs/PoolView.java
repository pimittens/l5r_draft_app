package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.Assets;

/**
 * a view that displays the pool of cards the user has picked
 */
public class PoolView {

    private float buffer = 25f;

    private Pool pool;

    private Rectangle dimen;

    private GlyphLayout layout;

    public PoolView(Rectangle dimen) {
        pool = new Pool();
        this.dimen = dimen;
        layout = new GlyphLayout();
    }

    public void addCardToPool(Card c, Assets assets) {
        pool.addCard(c, assets);
    }

    public Rectangle getDimen() {
        return dimen;
    }

    // todo: scrolling

    public void render(SpriteBatch batch, BitmapFont font) {
        float startX = dimen.x + buffer, nextY = dimen.y + dimen.height - buffer;
        String cardName;
        for (int i = 0; i < pool.size(); i++) {
            cardName = pool.getCard(i).getName().replace("_", " ");
            layout.setText(font, cardName);
            font.draw(batch, cardName + " x" + numFormat(pool.getQuantity(i)), startX, nextY);
            nextY -= layout.height * 2 + buffer;
        }
    }

    public void resize(int width, int height) {
        dimen = new Rectangle( width * 0.8f, 10, width * 0.2f - 10, height - 20);
    }

    private String numFormat(int i) {
        StringBuilder ret = new StringBuilder();
        for (int j = 0; j < i; j++) {
            ret.append("I");
        }
        return ret.toString();
    }
}
