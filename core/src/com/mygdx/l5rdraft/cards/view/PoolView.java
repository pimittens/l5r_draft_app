package com.mygdx.l5rdraft.cards.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.Assets;
import com.mygdx.l5rdraft.cards.Card;
import com.mygdx.l5rdraft.cards.Pool;

import java.util.ArrayList;
import java.util.List;

/**
 * a view that displays the pool of cards the user has picked
 */
public class PoolView {

    private float buffer = 10f;

    private Pool pool;
    private List<Texture> cardImages;
    private int camera;
    private float textHeight;

    private Rectangle dimen;

    private GlyphLayout layout;

    public PoolView(Rectangle dimen) {
        pool = new Pool();
        camera = 0;
        cardImages = new ArrayList<>();
        this.dimen = dimen;
        layout = new GlyphLayout();
        textHeight = 0;
    }

    public void updatePool(Pool p, Assets assets) {
        pool = p;
        generateCardImages(assets);
    }

    public Pool getPool() {
        return pool;
    }

    public Texture getCardImage(int pos) {
        return cardImages.get(pos);
    }

    public Rectangle getDimen() {
        return dimen;
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
            cardName = pool.getCard(i).getName().replace("_", " ");
            if (dimen.contains(startX, nextY + camera) && dimen.contains(startX, nextY + camera - textHeight)) {
                font.draw(batch, String.format("%s x%s", cardName, pool.getQuantity(i)), startX, nextY + camera);
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
}
