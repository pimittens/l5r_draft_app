package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.l5rdraft.input.DraftInputProcessor;
import com.mygdx.l5rdraft.L5RDraft;
import com.mygdx.l5rdraft.draft.Draft;
import com.mygdx.l5rdraft.cards.*;
import com.mygdx.l5rdraft.cards.view.PackView;
import com.mygdx.l5rdraft.cards.view.PoolView;

public class DraftScreen extends AbstractScreen {

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private DraftInputProcessor input;

    private FreeTypeFontGenerator fontGenerator;
    private BitmapFont font;

    private int height, width;

    private PackView packView;
    private PoolView poolView;

    private Draft draft;
    private String username;

    private boolean loadingTextures;

    public DraftScreen(L5RDraft app) {
        super(app);
        username = "user";
        draft = getApp().getDraft();
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        loadingTextures = true;
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Helvetica-Normal.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 30;
        font = fontGenerator.generateFont(parameters);
        batch = new SpriteBatch();
        input = new DraftInputProcessor(this);
        shapes = new ShapeRenderer();
        packView = new PackView(draft.getNextPack(username), new Rectangle(10, 10, Gdx.graphics.getWidth() * 0.75f - 20, Gdx.graphics.getHeight() - 20), getApp().getAssets());
        poolView = new PoolView(new Rectangle(Gdx.graphics.getWidth() * 0.75f, 10, Gdx.graphics.getWidth() * 0.25f - 10, Gdx.graphics.getHeight() * 0.95f - 20));
    }

    public int getHeight() {
        return height;
    }
    private int getWidth() {
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

    public void scroll(int mouseX, int mouseY, int amount) {
        if (poolView.getDimen().contains(mouseX, mouseY)) {
            poolView.scroll(amount);
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
        // todo: load the next pack beforehand
        if (packView.notHasPack()) {
            packView.setPack(draft.getNextPack(username), getApp().getAssets());
            if (!packView.notHasPack()) { // lol
                loadingTextures = true;
            }
        }
        if (loadingTextures) {
            getApp().getAssets().update();
            loadingTextures = !packView.updateCardImages(getApp().getAssets());
        }
        if (draft.draftIsOver()) {
            getApp().changeScreen(new DeckBuilderScreen(getApp(), poolView.getPool()));
        }
    }

    @Override
    public void render(float delta) {
        batch.begin();
        packView.render(batch);
        poolView.render(batch, font);
        font.draw(batch, String.format("pack %s/5", draft.getRoundNumber()), getWidth() * 0.85f, getHeight() * 0.98f);
        batch.end();

        // don't use shape renderer between batch.begin and batch.end
        Rectangle dimen = packView.getDimen();
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        dimen = poolView.getDimen();
        shapes.rect(dimen.x, dimen.y, dimen.width, dimen.height);
        //packView.renderShapes(shapes); // debug rectangles
        shapes.end();
    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
        this.width = width;
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        shapes.setProjectionMatrix(batch.getProjectionMatrix());
        packView.resize(width, height);
        poolView.resize(width, height, font);
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
