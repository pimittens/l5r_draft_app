package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.Assets;
import com.mygdx.l5rdraft.cards.Card;
import com.mygdx.l5rdraft.cards.Pack;
import com.mygdx.l5rdraft.cards.Pool;

import java.util.ArrayList;
import java.util.List;

public class PoolViewDeckBuilder {

    private Pool pool;
    private List<Texture> cardImages;
    private Rectangle dimen, arrowOneDimen, arrowTwoDimen;

    private Texture star, loadingImage, arrowOne, arrowTwo;

    private float buffer = 50f, cardWidth, cardHeight;

    private int page;

    public PoolViewDeckBuilder(Pool pool, Rectangle dimen, Assets assets) {
        setPool(pool, assets);
        star = assets.getStar();
        arrowOne = assets.getArrowOne();
        arrowTwo = assets.getArrowTwo();
        loadingImage = assets.getLoadingImage();
        this.dimen = dimen;
        cardWidth = (dimen.width - buffer * 5) / 4;
        cardHeight = (dimen.height - buffer * 3) / 2;
        arrowOneDimen = new Rectangle(dimen.x, dimen.y + dimen.height * 0.475f, dimen.width * 0.07f, dimen.height * 0.05f);
        arrowTwoDimen = new Rectangle(dimen.x + dimen.width * 0.93f, dimen.y + dimen.height * 0.475f, dimen.width * 0.07f, dimen.height * 0.05f);
        page = 0;
    }

    public void setPool(Pool pool, Assets assets) {
        this.pool = pool;
        if (pool != null) {
            generateCardImages(assets);
        }
    }

    /**
     * called when the user clicks the screen within the view
     *
     * @param screenX the x pos of the click event
     * @param screenY the y pos of the click event
     * @return true if a card was clicked, else false
     */
    public boolean click(int screenX, int screenY) {
        if (arrowOneDimen.contains(screenX, screenY)) {
            if (page >= 8) {
                page -= 8;
            }
            return false;
        }
        if (arrowTwoDimen.contains(screenX, screenY)) {
            if (page + 8 < pool.size()) {
                page += 8;
            }
            return false;
        }
        /*float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        Rectangle nextCardBounds;
        for (int i = page; i < page + 8; i++) {
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
        }*/
        return false;
    }

    public Rectangle getDimen() {
        return dimen;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        for (int i = page; i < pool.size(); i++) {
            if (!dimen.contains(new Rectangle(nextX, nextY, cardWidth, cardHeight))) {
                break;
            }
            if (cardImages.get(i) == null) {
                batch.draw(loadingImage, nextX, nextY, cardWidth, cardHeight);
            } else {
                font.draw(batch, String.format("%s/%s", pool.getQuantityInDeck(i), pool.getQuantity(i)), nextX + cardWidth * 0.45f, nextY + cardHeight + buffer * 0.6f);
                batch.draw(cardImages.get(i), nextX, nextY, cardWidth, cardHeight);
                if (pool.getCard(i).getRarity() == Card.RARITY.RARE) {
                    batch.draw(star, nextX + cardWidth * 0.45f, nextY, cardWidth * 0.1f, cardWidth * 0.1f);
                }
            }
            nextX += cardWidth + buffer;
            if (i % 4 == 3) {
                nextX = dimen.x + buffer;
                nextY += cardHeight + buffer;
            }
        }
        if (page > 0) {
            batch.draw(arrowOne, arrowOneDimen.x, arrowOneDimen.y, arrowOneDimen.width, arrowOneDimen.height);
        }
        if (page + 8 < pool.size()) {
            batch.draw(arrowTwo, arrowTwoDimen.x, arrowTwoDimen.y, arrowTwoDimen.width, arrowTwoDimen.height);
        }
    }

    public void renderShapes(ShapeRenderer shapes) {
        float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        for (int i = page; i < pool.size(); i++) {
            if (!dimen.contains(new Rectangle(nextX, nextY, cardWidth, cardHeight))) {
                break;
            }
            shapes.rect(nextX, nextY, cardWidth, cardHeight);
            nextX += cardWidth + buffer;
            if (i % 4 == 3) {
                nextX = dimen.x + buffer;
                nextY += cardHeight + buffer;
            }
        }
    }

    private void generateCardImages(Assets assets) {
        cardImages = new ArrayList<>();
        for (Card c : pool.getCards()) {
            cardImages.add(assets.get(c.getName()));
        }
    }

    public boolean updateCardImages(Assets assets) {
        boolean done = true;
        for (int i = 0; i < cardImages.size(); i++) {
            if (cardImages.get(i) == null) {
                if (assets.isLoaded(pool.getCard(i).getName())) {
                    cardImages.set(i, assets.get(pool.getCard(i).getName()));
                } else {
                    done = false;
                }
            }
        }
        return done;
    }

    public void resize(int width, int height) {
        dimen.setSize(width * 0.75f - 20, height - 20);
        cardWidth = (dimen.width - buffer * 5) / 4;
        cardHeight = (dimen.height - buffer * 3) / 2;arrowOneDimen = new Rectangle(dimen.x, dimen.y + dimen.height * 0.475f, dimen.width * 0.07f, dimen.height * 0.05f);
        arrowTwoDimen = new Rectangle(dimen.x + dimen.width * 0.93f, dimen.y + dimen.height * 0.475f, dimen.width * 0.07f, dimen.height * 0.05f);
    }
}
