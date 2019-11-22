package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * view that displays a pack of cards for the user to pick from
 */
public class PackView {

    private Pack pack;

    private Card clickedCard;

    private Rectangle dimen;

    // todo: use less rows/cols if there are less cards
    private float buffer = 5f, cardWidth, cardHeight;

    private ShapeRenderer shapes;

    public PackView(Pack pack, Rectangle dimen) {
        this.pack = pack;
        this.dimen = dimen;
        cardWidth = (dimen.width - buffer * 7) / 6;
        cardHeight = (dimen.height - buffer * 4) / 3;
        shapes = new ShapeRenderer();
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Rectangle getDimen() {
        return dimen;
    }

    public Card getClickedCard() {
        return clickedCard;
    }

    /**
     * called when the user clicks the screen within the view
     *
     * @param screenX the x pos of the click event
     * @param screenY the y pos of the click event
     * @return true if a card was clicked
     */
    public boolean click(int screenX, int screenY) {
        float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        Rectangle nextCardBounds;
        for (int i = 0; i < pack.size(); i++) {
            nextCardBounds = new Rectangle(nextX, nextY, cardWidth, cardHeight);
            if (nextCardBounds.contains(screenX, screenY)) {
                clickedCard = pack.popCard(i);
                return true;
            }
            nextX += cardWidth + buffer;
            if (i % 6 == 5) {
                nextX = dimen.x + buffer;
                nextY += cardHeight + buffer;
            }
        }
        return false;
    }

    public void render(SpriteBatch batch) {
        float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        Texture t;
        for (int i = 0; i < pack.size(); i++) {
            t = pack.getCardImage(i);
            batch.draw(t, nextX, nextY, cardWidth, cardHeight);
            nextX += cardWidth + buffer;
            if (i % 6 == 5) {
                nextX = dimen.x + buffer;
                nextY += cardHeight + buffer;
            }
        }
    }

    public void renderShapes(ShapeRenderer shapes) {
        float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        for (int i = 0; i < pack.size(); i++) {
            shapes.rect(nextX, nextY, cardWidth, cardHeight);
            nextX += cardWidth + buffer;
            if (i % 6 == 5) {
                nextX = dimen.x + buffer;
                nextY += cardHeight + buffer;
            }
        }
    }

    public void resize(int width, int height) {
        dimen = new Rectangle(10, 10, width * 0.8f - 20, height - 20);
        cardWidth = (dimen.width - buffer * 7) / 6;
        cardHeight = (dimen.height - buffer * 4) / 3;
    }
}
