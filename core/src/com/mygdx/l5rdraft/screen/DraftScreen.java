package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.Assets;
import com.mygdx.l5rdraft.InputProcessor;
import com.mygdx.l5rdraft.L5RDraft;
import com.mygdx.l5rdraft.drafters.Draft;
import com.mygdx.l5rdraft.packs.*;
import javafx.scene.layout.BackgroundImage;

public class DraftScreen extends AbstractScreen {

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private InputProcessor input;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameters;
    private BitmapFont font;

    private int height, width;

    private PackView packView;
    private PoolView poolView;

    private Draft draft;
    private String username;

    public DraftScreen(L5RDraft app) {
        super(app);
        username = "user";
        draft = getApp().getDraft();
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Helvetica-Normal.ttf"));
        parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 30;
        font = fontGenerator.generateFont(parameters);
        batch = new SpriteBatch();
        input = new InputProcessor(this);
        shapes = new ShapeRenderer();
        packView = new PackView(draft.getNextPack(username), new Rectangle(10, 10, Gdx.graphics.getWidth() * 0.8f - 20, Gdx.graphics.getHeight() - 20), getApp().getAssets());
        poolView = new PoolView(new Rectangle(Gdx.graphics.getWidth() * 0.8f, 10, Gdx.graphics.getWidth() * 0.2f - 10, Gdx.graphics.getHeight() * 0.95f - 20));
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
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
            if (packView.getDimen().contains(screenX, screenY)) {
                // click is inside the pack view, return true if they clicked a card
                // this will also remove the card from the pack if one was clicked
                if (packView.click(screenX, screenY)) {
                    // add the clicked card to the user's pool and check the pack back in
                    Card c = packView.getClickedCard();
                    Pack p = packView.getPack();
                    packView.clearPack();
                    draft.pushPack(p, c, username);
                    draft.update();
                    poolView.updatePool(draft.getPool(username));
                }
            }
        } else if (button == 1) {
            // todo: right click
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        fontGenerator.dispose();
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
        // todo: only get a new pack if the textures are loaded
        // also load the next pack before hand
        if (packView.notHasPack()) {
            packView.setPack(draft.getNextPack(username), getApp().getAssets());
        }
    }

    @Override
    public void render(float delta) {
        batch.begin();
        packView.render(batch);
        poolView.render(batch, font);
        if (draft.draftIsOver()) {
            font.draw(batch, "draft complete", getWidth() * 0.87f, getHeight() * 0.98f);
        } else {
            font.draw(batch, String.format("pack %s/5", draft.getRoundNumber()), getWidth() * 0.87f, getHeight() * 0.98f);
        }
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
        this.width = width;
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        packView.resize(width, height);
        poolView.resize(width, height);
        // todo: change font size here
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }
}
