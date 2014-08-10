package be.simonraes.breakout.screen;

import be.simonraes.breakout.input.InputHandler;
import be.simonraes.breakout.world.GameWorld;
import be.simonraes.breakout.world.Renderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * The screen for the gameplay.
 * Created by Simon Raes on 7/08/2014.
 */
public class GameScreen implements Screen {

    public static float screenWidth = 0, screenHeight = 0, gameWidth = 136, gameHeight = 0;
    private GameWorld world;
    private Renderer renderer;
    private InputHandler inputHandler;

    public GameScreen(){
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        gameHeight = screenHeight / (screenWidth / gameWidth);
        world = new GameWorld();
        renderer = new Renderer(world);
        inputHandler = new InputHandler(world,screenWidth / gameWidth, screenHeight / gameHeight);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render(float delta) {
        inputHandler.update();
        world.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }



}
