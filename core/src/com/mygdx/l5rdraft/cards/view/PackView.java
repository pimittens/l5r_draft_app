package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.Assets;
import com.mygdx.l5rdraft.cards.Card;
import com.mygdx.l5rdraft.cards.Pack;

import java.util.ArrayList;
import java.util.List;

/**
 * view that displays a pack of cards for the user to pick from
 */
public class PackView {

    private Pack pack;
    private List<Texture> cardImages;
    private Texture star, loadingImage;

    private Card clickedCard;

    private Rectangle dimen;

    // todo: use less rows/cols if there are less cards
    private float buffer = 5f, cardWidth, cardHeight;

    public PackView(Pack pack, Rectangle dimen, Assets assets) {
        setPack(pack, assets);
        star = assets.getStar();
        loadingImage = assets.getLoadingImage();
        this.dimen = dimen;
        cardWidth = (dimen.width - buffer * 7) / 6;
        cardHeight = (dimen.height - buffer * 4) / 3;
    }

    public void setPack(Pack pack, Assets assets) {
        this.pack = pack;
        if (pack != null) {
            generateCardImages(assets);
        }
    }

    public void clearPack() {
        pack = null;
    }

    public Pack getPack() {
        return pack;
    }

    public boolean notHasPack() {
        return pack == null;
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
     * @return true if a card was clicked, else false
     */
    public boolean click(int screenX, int screenY) {
        if (notHasPack()) {
            return false;
        }
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
        if (notHasPack()) {
            return;
        }
        float nextX = dimen.x + buffer, nextY = dimen.y + buffer;
        for (int i = 0; i < pack.size(); i++) {
            if (cardImages.get(i) == null) {
                batch.draw(loadingImage, nextX, nextY, cardWidth, cardHeight);
            } else {
                batch.draw(cardImages.get(i), nextX, nextY, cardWidth, cardHeight);
                if (pack.getCard(i).getRarity() == Card.RARITY.RARE) {
                    batch.draw(star, nextX + cardWidth * 0.45f, nextY, cardWidth * 0.1f, cardWidth * 0.1f);
                }
            }
            nextX += cardWidth + buffer;
            if (i % 6 == 5) {
                nextX = dimen.x + buffer;
                nextY += cardHeight + buffer;
            }
        }
    }

    public void renderShapes(ShapeRenderer shapes) {
        if (notHasPack()) {
            return;
        }
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
        dimen.setSize(width * 0.75f - 20, height - 20);
        cardWidth = (dimen.width - buffer * 7) / 6;
        cardHeight = (dimen.height - buffer * 4) / 3;
    }

    private void generateCardImages(Assets assets) {
        cardImages = new ArrayList<>();
        for (Card c : pack.getCards()) {
            cardImages.add(assets.get(c.getName()));
        }
    }

    public boolean updateCardImages(Assets assets) {
        if (notHasPack()) {
            return false;
        }
        boolean done = true;
        for (int i = 0; i < cardImages.size(); i++) {
            if (cardImages.get(i) == null) {
                if (assets.isLoaded(pack.getCard(i).getName())) {
                    cardImages.set(i, assets.get(pack.getCard(i).getName()));
                } else {
                    done = false;
                }
            }
        }
        return done;
    }
}
