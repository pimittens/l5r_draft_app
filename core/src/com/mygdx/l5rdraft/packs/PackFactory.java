package com.mygdx.l5rdraft.packs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.l5rdraft.Assets;

import java.util.List;

public class PackFactory {

    public static Pack createPack(AssetManager assets, List<Card> cards) {
        Pack ret = new Pack();
        Card c;
        Texture t;
        for (int i = 0; i < 15; i++) {
            c = cards.get(i);
            t = assets.get(Assets.CARD_PATH + c.getName() + ".png", Texture.class);
            t.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
            ret.addCard(c, t);
        }
        return ret;
    }
}
