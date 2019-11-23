package com.mygdx.l5rdraft;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static final String CARD_PATH = "cards/";

    private AssetManager manager;
    private TextureLoader.TextureParameter textureParameter;

    public Assets() {
        manager = new AssetManager();
        textureParameter = new TextureLoader.TextureParameter();
        textureParameter.genMipMaps = true;
    }

    /*public void load(List<Card> cards) {
        for (Card c : cards) {
            manager.load(CARD_PATH + c.getName() + ".png", Texture.class, textureParameter);
        }
    }*/

    /**
     * gets the texture for a card. loads the texture if it is not loaded yet
     *
     * @param cardName the name of the card
     * @return the texture for the card
     */
    public Texture get(String cardName) {
        if (!manager.isLoaded(CARD_PATH + cardName + ".png", Texture.class)) {
            manager.load(CARD_PATH + cardName + ".png", Texture.class, textureParameter);
            manager.finishLoading();
        }
        Texture t = manager.get(CARD_PATH + cardName + ".png", Texture.class);
        t.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
        return t;
    }

    public void dispose() {
        manager.dispose();
    }
}
