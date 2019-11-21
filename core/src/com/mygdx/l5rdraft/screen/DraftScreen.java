package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.InputProcessor;
import com.mygdx.l5rdraft.L5RDraft;
import com.mygdx.l5rdraft.packs.PackFactory;
import com.mygdx.l5rdraft.packs.PackView;
import com.mygdx.l5rdraft.packs.PoolView;

public class DraftScreen extends AbstractScreen {

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private InputProcessor input;

    private int height;

    private PackView packView;
    private PoolView poolView;

    public DraftScreen(L5RDraft app) {
        super(app);
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        input = new InputProcessor(this);
        shapes = new ShapeRenderer();
        packView = new PackView(PackFactory.createPack(app.getAssets(), app.getCardList()), new Rectangle(10, 10, Gdx.graphics.getWidth() * 0.8f - 20, Gdx.graphics.getHeight() - 20));
        poolView = new PoolView(new Rectangle(Gdx.graphics.getWidth() * 0.8f, 10, Gdx.graphics.getWidth() * 0.2f - 10, Gdx.graphics.getHeight() - 20));
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
        if (packView.getDimen().contains(screenX, screenY)) {
            // click is inside the pack view, return true if they clicked a card
            // this will also remove the card from the pack if one was clicked
            if (packView.click(screenX, screenY)) {
                // add the clicked card to the user's pool and give them a new pack
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        // update input processors
        //playerMovementProcessor.update(delta);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        packView.render(batch);
        batch.end();

        // don't use shape renderer between batch.begin and batch.end
        Rectangle dimen = packView.getDimen();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        dimen = poolView.getDimen();
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        packView.renderShapes(shapes); // debug rectangles
        shapes.end();
    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        packView.resize(width, height);
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
