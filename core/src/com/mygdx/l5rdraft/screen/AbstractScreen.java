package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Screen;
import com.mygdx.l5rdraft.L5RDraft;

public abstract class AbstractScreen implements Screen {

    private L5RDraft app;

    AbstractScreen(L5RDraft app) {
        this.app = app;
    }

    @Override
    public abstract void dispose();

    @Override
    public abstract void hide();

    @Override
    public abstract void pause();

    public abstract void update(float delta);

    @Override
    public abstract void render(float delta);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void resume();

    @Override
    public abstract void show();

    public L5RDraft getApp() {
        return app;
    }
}
