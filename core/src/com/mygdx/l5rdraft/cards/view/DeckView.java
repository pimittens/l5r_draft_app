package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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

    public void updatePool(Pool p) {
        pool = p;
    }

    public Pool getPool() {
        return pool;
    }

    public Rectangle getDimen() {
        return dimen;
    }

    public void addCard(Card c) {
        pool.addCard(c);
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
        float nextY = dimen.y + dimen.height - buffer;
        for (int i = 0; i < pool.size(); i++) { // todo: need to start at a different card depending on camera
            if (pool.getQuantityInDeck(i) < 1) {
                continue;
            }
            if (Math.abs(screenY - nextY) < 5) {
                clickedCard = pool.getCard(i);
                pool.changeQuantityInDeck(i, -1);
                return true;
            }
            nextY -= textHeight * 2 + buffer;
        }
        return false;
    }

    public void scroll(int amount) {
        if ((textHeight * 2 + buffer) * pool.size() < dimen.height) {
            // if the number of text lines doesn't span the view don't move the camera
            camera = 0;
            return;
        }
        if (amount > 0 && (dimen.y + dimen.height - buffer) - ((textHeight * 2 + buffer) * pool.size()) + camera > dimen.y) {
            // if the last item is at the bottom don't let them scroll down anymore
            return;
        }
        camera += amount * 20;
        if (camera < 0) {
            // camera should never be less than 0 bc that's where the top of the list is
            camera = 0;
        }
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

    public void resize(int width, int height, BitmapFont font) {
        dimen.setPosition(width * 0.75f, 10);
        dimen.setSize(width * 0.25f - 10, height * 0.95f - 20);
        layout.setText(font, "TEST");
        textHeight = layout.height;
    }
}
