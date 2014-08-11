package be.simonraes.breakout.input;

import be.simonraes.breakout.actors.Ball;
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
                world.launchCommand();
                return true;
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


        if (screenX < GameScreen.screenWidth / 2) {
            leftPressed = true;
        } else {
            rightPressed = true;
        }

        if(leftPressed && rightPressed){
            world.launchCommand();
        }

//        if (world.getGameState() == GameWorld.GameState.READYFORLAUNCH) {
//            System.out.println("ready for launch");
//            System.out.println(leftPressed+" "+rightPressed);
//            if(leftPressed && rightPressed){
//                world.setGameState(GameWorld.GameState.RUNNING);
//                for(Ball ball : world.getBalls()){
//                    ball.launch();
//                }            }
//
//        } else if (world.getGameState() == GameWorld.GameState.LEVELCOMPLETE) {
//            System.out.println("level complete");
//
//            world.startNextLevel();
//        } else if (world.getGameState() == GameWorld.GameState.LIFEOVER) {
//            System.out.println("game over");
//
//            world.restart();
//        }


        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        // Todo: input bug here:
        // If user presses down on left side, drags over to right side and releases there it will count as a
        // right-up, causing the left to get stuck in the pressed state until a touchUp is registered in that
        // area.

        if (screenX < GameScreen.screenWidth / 2) {
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


}
