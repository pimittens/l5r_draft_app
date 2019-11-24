package com.mygdx.l5rdraft;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

public class Assets {

    public static final String CARD_PATH = "cards/";
    public static final String STAR = "star.png";

    private AssetManager manager;
    private TextureLoader.TextureParameter textureParameter;

    public Assets() {
        manager = new AssetManager();
        textureParameter = new TextureLoader.TextureParameter();
        textureParameter.genMipMaps = true;
    }

    public void finishLoading() {
        manager.finishLoading();
    }

    public void load() {
        manager.load(STAR, Texture.class, textureParameter);
    }

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

    public Texture getStar() {
        Texture t = manager.get(STAR, Texture.class);
        t.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
        return t;
    }

    public void dispose() {
        manager.dispose();
    }
}
