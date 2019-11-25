package com.mygdx.l5rdraft.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.l5rdraft.L5RDraft;

public class TestScreen extends AbstractScreen {

    private InputProcessorTest input;

    public TestScreen(L5RDraft app) {
        super(app);
        input = new InputProcessorTest();
    }

    @Override
    public void dispose() {
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
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(input);
    }

    private static class InputProcessorTest extends InputAdapter {

        public InputProcessorTest() {
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
            System.out.println(button);
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return super.touchUp(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return super.touchDragged(screenX, screenY, pointer);
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return super.mouseMoved(screenX, screenY);
        }

        @Override
        public boolean scrolled(int amount) {
            return super.scrolled(amount);
        }
    }

}
