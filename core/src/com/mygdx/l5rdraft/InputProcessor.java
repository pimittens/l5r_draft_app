package com.mygdx.l5rdraft;

import com.badlogic.gdx.InputAdapter;
import com.mygdx.l5rdraft.screen.DraftScreen;

public class InputProcessor extends InputAdapter {

    private DraftScreen screen;
    private int mouseX, mouseY;

    public InputProcessor(DraftScreen screen) {
        this.screen = screen;
        mouseX = -1;
        mouseY = -1;
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
        mouseX = screenX;
        mouseY = screen.getHeight() - screenY;
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        // down is 1, up is -1
        screen.scroll(mouseX, mouseY, amount);
        return super.scrolled(amount);
    }
}
