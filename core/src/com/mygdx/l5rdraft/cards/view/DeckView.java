package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.Assets;
import com.mygdx.l5rdraft.cards.Card;
import com.mygdx.l5rdraft.cards.Pool;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * pool view with some extra functionality for adding and removing cards
 * will probably sort by deck type later
 */
public class DeckView {

    private float buffer = 10f;

    private Pool pool;
    private List<Texture> cardImages;
    private int camera;
    private float textHeight;

    private Card clickedCard;

    private Rectangle dimen;

    private GlyphLayout layout;

    public DeckView(Rectangle dimen) {
        pool = new Pool();
        camera = 0;
        cardImages = new ArrayList<>();
        this.dimen = dimen;
        layout = new GlyphLayout();
        textHeight = 0;
    }

    /*public void updatePool(Pool p) {
        pool = p;
    }

    public Pool getPool() {
        return pool;
    }*/

    public Texture getCardImage(int pos) {
        return cardImages.get(pos);
    }

    public Rectangle getDimen() {
        return dimen;
    }

    public void addCard(Card c, Assets assets) {
        if (pool.addCard(c)) {
            cardImages.add(assets.get(c.getName()));
        }
        pool.changeQuantityInDeck(pool.getPos(c), 1);
    }

    public Card getClickedCard() {
        return clickedCard;
    }

    /**
     * called when the user clicks the screen within the view
     *
     * @param screenY the y pos of the click event
     * @return true if a card was clicked, else false
     */
    public boolean click(int screenY) {
        float nextY = dimen.y + dimen.height - buffer + camera;
        for (int i = 0; i < pool.size(); i++) {
            if (pool.getQuantityInDeck(i) < 1) {
                continue;
            }
            if (screenY > nextY - textHeight && screenY < nextY) {
                clickedCard = pool.getCard(i);
                pool.changeQuantityInDeck(i, -1);
                scroll(0);
                return true;
            }
            nextY -= textHeight * 2 + buffer;
        }
        return false;
    }

    public void scroll(int amount) {
        if ((textHeight * 2 + buffer) * pool.sizeNoZeroes() < dimen.height) {
            // if the number of text lines doesn't span the view don't move the camera
            camera = 0;
            return;
        }
        if (amount >= 0 && (dimen.y + dimen.height - buffer) - ((textHeight * 2 + buffer) * pool.sizeNoZeroes()) + camera + amount * 20 >= dimen.y) {
            // if the last item is at the bottom don't let them scroll down anymore
            // also used to update the bottom of the list when an item is removed
            camera = (int) ((textHeight * 2 + buffer) * pool.sizeNoZeroes() - dimen.height + buffer);
            return;
        }
        camera += amount * 20;
        if (camera < 0) {
            // camera should never be less than 0 bc that's where the top of the list is
            camera = 0;
        }
    }

    /**
     * called when the user moves the mouse within the view
     * checks if the mouse is over a list item
     *
     * @param screenY the y pos
     * @return the index of the hovered item, -1 if no item is hovered
     */
    public int mouseMoved(int screenY) {
        float nextY = dimen.y + dimen.height - buffer + camera;
        for (int i = 0; i < pool.size(); i++) {
            if (pool.getQuantityInDeck(i) < 1) {
                continue;
            }
            if (screenY > nextY - textHeight && screenY < nextY) {
                return i;
            }
            nextY -= textHeight * 2 + buffer;
        }
        return -1;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        if (pool == null) {
            return;
        }
        float startX = dimen.x + buffer, nextY = dimen.y + dimen.height - buffer;
        String cardName;
        for (int i = 0; i < pool.size(); i++) {
            if (pool.getQuantityInDeck(i) < 1) {
                continue;
            }
            cardName = pool.getCard(i).getName().replace("_", " ");
            if (dimen.contains(startX, nextY + camera) && dimen.contains(startX, nextY + camera - textHeight)) {
                font.draw(batch, String.format("%s x%s", cardName, pool.getQuantityInDeck(i)), startX, nextY + camera);
            }
            nextY -= textHeight * 2 + buffer;
        }
    }

    public void renderShapes(ShapeRenderer shapes) {
        // debug shapes
        float nextY = dimen.y + dimen.height - buffer + camera;
        for (int i = 0; i < pool.size(); i++) {
            if (pool.getQuantityInDeck(i) < 1) {
                continue;
            }
            if (dimen.contains(dimen.x + 1, nextY) && dimen.contains(dimen.x + 1, nextY - textHeight)) {
                shapes.rect(dimen.x, nextY - textHeight, dimen.width, textHeight);
            }
            nextY -= textHeight * 2 + buffer;
        }
    }

    public void resize(int width, int height, BitmapFont font) {
        dimen.setPosition(width * 0.75f, 10);
        dimen.setSize(width * 0.25f - 10, height * 0.95f - 20);
        layout.setText(font, "TEST");
        textHeight = layout.height;
    }



    /*private void generateCardImages(Assets assets) {
        cardImages = new ArrayList<>();
        for (Card c : pool.getCards()) {
            cardImages.add(assets.get(c.getName()));
        }
    }*/

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
}
