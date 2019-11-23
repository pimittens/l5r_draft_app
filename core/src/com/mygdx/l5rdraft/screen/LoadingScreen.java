package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.l5rdraft.L5RDraft;

public class LoadingScreen extends AbstractScreen {

    private ShapeRenderer shapes;
    private SpriteBatch batch;

    private int screenWidth;

    public LoadingScreen(L5RDraft app) {
        super(app);
        screenWidth = Gdx.graphics.getWidth();
        shapes = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void dispose() {
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
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.RED);
        //shapes.rect(10, 10, (screenWidth - 20) * getApp().getAssets().getProgress(), 100);
        shapes.end();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.WHITE);
        shapes.rect(10, 10, screenWidth - 20, 100);
        shapes.end();
    }

    @Override
    public void resize(int width, int height) {
        screenWidth = width;
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }
}
