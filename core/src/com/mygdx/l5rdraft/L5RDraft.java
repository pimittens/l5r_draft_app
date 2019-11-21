package com.mygdx.l5rdraft;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.l5rdraft.packs.Card;
import com.mygdx.l5rdraft.screen.AbstractScreen;
import com.mygdx.l5rdraft.screen.DraftScreen;
import com.mygdx.l5rdraft.screen.LoadingScreen;

import java.util.ArrayList;
import java.util.List;

public class L5RDraft extends Game {

    private Assets assets;
    private DraftScreen draftScreen;
    private LoadingScreen loadingScreen;

    // list of all cards used
    private List<Card> cardList;

    @Override
    public void create() {
        Gdx.app.getGraphics().setTitle("l5r draft");
        loadAllCards();
        assets = new Assets();
        assets.load(cardList);
        //assets.manager.finishLoading();
        loadingScreen = new LoadingScreen(this);
        this.setScreen(loadingScreen);
    }

    public AssetManager getAssets() {
        return assets.manager;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    @Override
    public void render() {
        // update
        if (getScreen() instanceof AbstractScreen) {
            ((AbstractScreen) getScreen()).update(Gdx.graphics.getDeltaTime());
        }
        if (getScreen() instanceof LoadingScreen) {
            try {
                if (getAssets().update()) {
                    draftScreen = new DraftScreen(this);
                    setScreen(draftScreen);
                }
            } catch (GdxRuntimeException ex) {
                ex.printStackTrace();
            }
        }
        // render
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        assets.dispose();
    }

    private void loadAllCards() {
        cardList = new ArrayList<>();
        loadCards("neutral");
        // disabled for testing loadCards("crab");
        // disabled for testing loadCards("crane");
        // disabled for testing loadCards("dragon");
        // disabled for testing loadCards("lion");
        // disabled for testing loadCards("phoenix");
        // disabled for testing loadCards("scorpion");
        // disabled for testing loadCards("unicorn");
    }

    private void loadCards(String fileName) {
        JsonReader reader = new JsonReader();
        JsonValue base = reader.parse(Gdx.files.internal("json/" + fileName + ".json"));
        Card.RARITY rarity;
        for (JsonValue card : base.get("cards")) {
            switch (card.getString("rarity")) {
                case "rare":
                    rarity = Card.RARITY.RARE;
                    break;
                case "province":
                    rarity = Card.RARITY.PROVINCE;
                    break;
                case "super_common":
                    rarity = Card.RARITY.SUPER_COMMON;
                    break;
                case "common":
                default:
                    rarity = Card.RARITY.COMMON;
                    break;
            }
            if (rarity != Card.RARITY.PROVINCE) {
            	// for testing since we don't have the rest of the images yet
            	continue;
			}
            cardList.add(new Card(card.getString("name"), rarity));
        }
    }
}
