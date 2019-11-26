package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
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
    private Rectangle dimen;

    private Texture star, loadingImage;

    private float buffer = 50f, cardWidth, cardHeight;

    private int page;

    public PoolViewDeckBuilder(Pool pool, Rectangle dimen, Assets assets) {
        setPool(pool, assets);
        star = assets.getStar();
        loadingImage = assets.getLoadingImage();
        this.dimen = dimen;
        cardWidth = (dimen.width - buffer * 5) / 4;
        cardHeight = (dimen.height - buffer * 3) / 2;
        page = 0;
    }



    public void setPool(Pool pool, Assets assets) {
        this.pool = pool;
        if (pool != null) {
            generateCardImages(assets);
        }
    }

    public Rectangle getDimen() {
        return dimen;
    }

    public void render(SpriteBatch batch) {
        float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        for (int i = page; i < pool.size(); i++) {
            if (!dimen.contains(new Rectangle(nextX, nextY, cardWidth, cardHeight))) {
                break;
            }
            if (cardImages.get(i) == null) {
                batch.draw(loadingImage, nextX, nextY, cardWidth, cardHeight);
            } else {
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
        cardHeight = (dimen.height - buffer * 3) / 2;
    }
}
