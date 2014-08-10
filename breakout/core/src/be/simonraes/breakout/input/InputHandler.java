package be.simonraes.breakout.input;

import be.simonraes.breakout.screen.GameScreen;
import be.simonraes.breakout.world.GameWorld;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Simon Raes on 7/08/2014.
 */
public class InputHandler implements InputProcessor {

    private GameWorld world;
    private boolean leftPressed, rightPressed;
    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
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
                if (world.getGameState() == GameWorld.GameState.READYFORLAUNCH) {
                    world.setGameState(GameWorld.GameState.RUNNING);
                    world.getBall().launch();
                } else if (world.getGameState() == GameWorld.GameState.LEVELCOMPLETE) {
                    world.startNextLevel();
                } else if (world.getGameState() == GameWorld.GameState.GAMEOVER) {
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

        System.out.println("touch at " + screenX);


        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        System.out.println("scaled touch point " + screenX);
        System.out.println("gameWidth " + GameScreen.gameWidth);
        System.out.println("screenwidht" + GameScreen.screenWidth);

        if (world.getGameState() == GameWorld.GameState.READYFORLAUNCH) {
            world.setGameState(GameWorld.GameState.RUNNING);
            world.getBall().launch();
        } else if (world.getGameState() == GameWorld.GameState.LEVELCOMPLETE) {
            world.startNextLevel();
        } else if (world.getGameState() == GameWorld.GameState.GAMEOVER) {
            world.restart();
        } else if (world.getGameState() == GameWorld.GameState.RUNNING) {
            if (screenX < GameScreen.gameWidth / 2) {
                leftPressed = true;
                return true;
            } else {
                rightPressed = true;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (screenX < GameScreen.gameWidth / 2) {
            leftPressed = false;
            return true;
        } else {
            rightPressed = false;
            return true;
        }

//        return false;
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

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }
}
