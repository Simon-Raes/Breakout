package be.simonraes.breakout.input;

import be.simonraes.breakout.world.GameWorld;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Simon Raes on 7/08/2014.
 */
public class InputHandler implements InputProcessor {

    private GameWorld world;
    private boolean leftPressed, rightPressed;

    public InputHandler(GameWorld world) {
        this.world = world;
    }

    public void update() {
        if (leftPressed && !rightPressed) {
            world.getPaddle().goLeft();
        } else if (rightPressed && !leftPressed) {
            world.getPaddle().goRight();
        } else {
            world.getPaddle().stopMoving();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                leftPressed = true;
                return true;

            case Input.Keys.RIGHT:
                rightPressed = true;
                return true;

            case Input.Keys.SPACE:
                if(world.getGameState() == GameWorld.GameState.RUNNING){
                    world.getBall().launch();
                } else if(world.getGameState() == GameWorld.GameState.GAMEOVER){
                    world.restart();
                }

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                leftPressed = false;
                return true;

            case Input.Keys.RIGHT:
                rightPressed = false;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
