package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * a view that displays the pool of cards the user has picked
 */
public class PoolView {

    private Pack pool;

    private Rectangle dimen;

    public PoolView(Rectangle dimen) {
        pool = new Pack();
        this.dimen = dimen;
    }

    public Rectangle getDimen() {
        return dimen;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < pool.size(); i++) {

        }
    }

    public void resize(int width, int height) {
        dimen = new Rectangle( width * 0.8f, 10, width * 0.2f - 10, height - 20);
    }
}
