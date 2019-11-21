package com.mygdx.l5rdraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.l5rdraft.packs.Card;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Assets {

    public static final String CARD_PATH = "cards/";

    public AssetManager manager;

    public Assets() {
        manager = new AssetManager();
    }

    public void load(List<Card> cards) {
        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.genMipMaps = true;
        for (Card c : cards) {
            manager.load(CARD_PATH + c.getName() + ".png", Texture.class, textureParameter);
        }
    }

    public void dispose() {
        manager.dispose();
    }
}
