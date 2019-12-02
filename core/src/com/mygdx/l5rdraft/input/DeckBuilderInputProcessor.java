package com.mygdx.l5rdraft.input;

import com.badlogic.gdx.InputAdapter;
import com.mygdx.l5rdraft.screen.DeckBuilderScreen;

public class DeckBuilderInputProcessor extends InputAdapter {

    private DeckBuilderScreen screen;

    public DeckBuilderInputProcessor(DeckBuilderScreen screen) {
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screen.click(screenX, screen.getHeight() - screenY, button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //System.out.println("test1 - " + pointer);
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screen.mouseMoved(screenX, screen.getHeight() - screenY);
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        // down is 1, up is -1
        screen.scroll(amount);
        return super.scrolled(amount);
    }
}
