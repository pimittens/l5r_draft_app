package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.L5RDraft;
import com.mygdx.l5rdraft.cards.Card;
import com.mygdx.l5rdraft.cards.Pack;
import com.mygdx.l5rdraft.cards.Pool;
import com.mygdx.l5rdraft.cards.view.DeckView;
import com.mygdx.l5rdraft.cards.view.PoolViewDeckBuilder;
import com.mygdx.l5rdraft.input.DeckBuilderInputProcessor;

public class DeckBuilderScreen extends AbstractScreen {

    private PoolViewDeckBuilder poolView;
    private DeckView deckView;

    private FreeTypeFontGenerator fontGenerator;
    private BitmapFont font;

    private DeckBuilderInputProcessor input;

    private int height;

    private SpriteBatch batch;
    private ShapeRenderer shapes;

    private boolean loadingTextures;

    public DeckBuilderScreen(L5RDraft app, Pool pool) {
        super(app);
        loadingTextures = true;
        height = Gdx.graphics.getHeight();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Helvetica-Normal.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 30;
        font = fontGenerator.generateFont(parameters);
        input = new DeckBuilderInputProcessor(this);
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        poolView = new PoolViewDeckBuilder(pool, new Rectangle(10, 10, Gdx.graphics.getWidth() * 0.75f - 20, Gdx.graphics.getHeight() - 20), getApp().getAssets());
        deckView = new DeckView(new Rectangle(Gdx.graphics.getWidth() * 0.75f + 10, 10, Gdx.graphics.getWidth() * 0.25f - 20, Gdx.graphics.getHeight() - 20));
    }

    public int getHeight() {
        return height;
    }

    /**
     * called when the user clicks the screen (or taps on a mobile device)
     *
     * @param screenX the x pos
     * @param screenY the y pos
     * @param button  the mouse button
     */
    public void click(int screenX, int screenY, int button) {
        if (button == 0) {
            // left click
            if (poolView.getDimen().contains(screenX, screenY)) {
                // click is inside the pool view, return true if they clicked a card
                if (poolView.click(screenX, screenY)) {
                    // add the clicked card to the deck
                    deckView.addCard(poolView.getClickedCard());
                }
            }
            if (deckView.getDimen().contains(screenX, screenY)) {
                // click is inside the deck view, return true if they clicked a card
                if (deckView.click(screenY)) {
                    // add the clicked card back to the pool
                    poolView.addCard(deckView.getClickedCard());
                }
            }
        } else if (button == 1) {
            // todo: right click
        }
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        batch.dispose();
        shapes.dispose();
        fontGenerator.dispose();
        font.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        if (loadingTextures) {
            getApp().getAssets().update();
            loadingTextures = !poolView.updateCardImages(getApp().getAssets());
        }
    }

    @Override
    public void render(float delta) {
        batch.begin();
        poolView.render(batch, font);
        deckView.render(batch, font);
        batch.end();

        Rectangle dimen = poolView.getDimen();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        dimen = deckView.getDimen();
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        //poolView.renderShapes(shapes);
        shapes.end();
    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        poolView.resize(width, height);
        deckView.resize(width, height, font);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }
}
