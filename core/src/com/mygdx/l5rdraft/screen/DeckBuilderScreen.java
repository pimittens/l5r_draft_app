package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.L5RDraft;
import com.mygdx.l5rdraft.cards.view.DeckView;
import com.mygdx.l5rdraft.cards.view.PoolViewDeckBuilder;
import com.mygdx.l5rdraft.input.DeckBuilderInputProcessor;

public class DeckBuilderScreen extends AbstractScreen {

    private PoolViewDeckBuilder poolView;
    private DeckView deckView;

    private DeckBuilderInputProcessor input;

    private SpriteBatch batch;
    private ShapeRenderer shapes;

    DeckBuilderScreen(L5RDraft app) {
        super(app);
        input = new DeckBuilderInputProcessor();
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        poolView = new PoolViewDeckBuilder(new Rectangle(10, 10, Gdx.graphics.getWidth() * 0.75f - 20, Gdx.graphics.getHeight() - 20));
        deckView = new DeckView(new Rectangle(10, Gdx.graphics.getWidth() * 0.75f + 10, Gdx.graphics.getWidth() * 0.25f - 20, Gdx.graphics.getHeight() - 20));
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        batch.dispose();
        shapes.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        poolView.render(batch);
        batch.end();

        Rectangle dimen = poolView.getDimen();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        dimen = deckView.getDimen();
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        shapes.end();
    }

    @Override
    public void resize(int width, int height) {
        poolView.resize(width, height);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }
}
