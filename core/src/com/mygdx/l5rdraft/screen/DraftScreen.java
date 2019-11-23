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

    private int height;

    private PackView packView;
    private PoolView poolView;

    private Draft draft;
    private String username;

    public DraftScreen(L5RDraft app) {
        super(app);
        username = "user";
        draft = getApp().getDraft();
        height = Gdx.graphics.getHeight();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("BTTTRIAL.otf"));
        parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 30;
        font = fontGenerator.generateFont(parameters);
        batch = new SpriteBatch();
        input = new InputProcessor(this);
        shapes = new ShapeRenderer();
        packView = new PackView(PackFactory.createPack(app.getCardList()), new Rectangle(10, 10, Gdx.graphics.getWidth() * 0.8f - 20, Gdx.graphics.getHeight() - 20), getApp().getAssets());
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
        if (button == 0) {
            // left click
            if (packView.getDimen().contains(screenX, screenY)) {
                // click is inside the pack view, return true if they clicked a card
                // this will also remove the card from the pack if one was clicked
                if (packView.click(screenX, screenY)) {
                    // add the clicked card to the user's pool and give them a new pack
                    Card c = packView.getClickedCard();
                    Pack p = packView.getPack();
                    draft.pushPack(p, c, username);
                    draft.update();
                    packView.setPack(draft.getNextPack(username), getApp().getAssets());
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
    }

    @Override
    public void render(float delta) {
        batch.begin();
        packView.render(batch);
        poolView.render(batch, font);
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
