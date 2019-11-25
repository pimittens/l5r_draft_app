package com.mygdx.l5rdraft;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    private static final String CARD_PATH = "cards/";
    private static final String STAR = "star.png";
    private static final String LOADING_IMAGE = "loading_image.png";

    private AssetManager manager;
    private TextureLoader.TextureParameter textureParameter;

    Assets() {
        manager = new AssetManager();
        textureParameter = new TextureLoader.TextureParameter();
        textureParameter.genMipMaps = true;
    }

    void finishLoading() {
        manager.finishLoading();
    }

    void load() {
        manager.load(STAR, Texture.class, textureParameter);
        manager.load(LOADING_IMAGE, Texture.class, textureParameter);
    }

    public boolean update() {
        return manager.update();
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
            return null;
        }
        Texture t = manager.get(CARD_PATH + cardName + ".png", Texture.class);
        t.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
        return t;
    }

    public boolean isLoaded(String cardName) {
        return manager.isLoaded(CARD_PATH + cardName + ".png", Texture.class);
    }

    public Texture getStar() {
        Texture t = manager.get(STAR, Texture.class);
        t.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
        return t;
    }

    public Texture getLoadingImage() {
        Texture t = manager.get(LOADING_IMAGE, Texture.class);
        t.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
        return t;
    }

    void dispose() {
        manager.dispose();
    }
}
