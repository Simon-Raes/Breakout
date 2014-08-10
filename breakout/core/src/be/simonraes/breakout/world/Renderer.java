package be.simonraes.breakout.world;

import be.simonraes.breakout.actors.BasicBlock;
import be.simonraes.breakout.actors.Block;
import be.simonraes.breakout.actors.StrongBlock;
import be.simonraes.breakout.screen.GameScreen;
import be.simonraes.breakout.util.AssetLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by Simon Raes on 7/08/2014.
 */
public class Renderer {

    private GameWorld world;

    private ArrayList<BasicBlock> blocks;

    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private BitmapFont font;


    public Renderer(GameWorld world) {
        this.world = world;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, GameScreen.gameWidth, GameScreen.gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        font = new BitmapFont(true);
        font.setScale(.5f, .5f);
        font.setColor(Color.MAROON);
    }

    public void render(float delta) {


        if (world.getGameState() == GameWorld.GameState.LEVELCOMPLETE) {
            drawLevelWon();
        }

        if (world.getGameState() == GameWorld.GameState.GAMEOVER) {
            drawGameOver();
        } else {
            drawGamePlay();
            drawHud();
        }


    }

    private void drawGamePlay() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw background
        shapeRenderer.setColor(Color.OLIVE);
        shapeRenderer.rect(0, 0, GameScreen.gameWidth, GameScreen.gameHeight);


        // Draw blocks
        for (Block block : world.getLevel().getBlocks()) {
            if (block.isAlive()) {
                if (block instanceof BasicBlock) {
                    shapeRenderer.setColor(Color.YELLOW);
                } else if (block instanceof StrongBlock) {
                    switch (block.getHitPoints()) {
                        case 3:
                            shapeRenderer.setColor(Color.BLACK);
                            break;
                        case 2:
                            shapeRenderer.setColor(Color.DARK_GRAY);
                            break;
                        case 1:
                            shapeRenderer.setColor(Color.LIGHT_GRAY);
                            break;
                    }
                }
                shapeRenderer.rect(block.getX(), block.getY(), block.getWidth(), block.getHeight());
            }
        }

        shapeRenderer.end();

        // Draw rail
        batcher.begin();
        for (int i = 0; i < GameScreen.gameWidth; i += AssetLoader.rail.getRegionWidth()) {
            batcher.draw(AssetLoader.rail, i, world.getPaddle().getY() + 1, AssetLoader.rail.getRegionWidth(), world.getPaddle().getHeight() - 2);
        }

        // Draw text (if needed)
        if (world.getGameState() == GameWorld.GameState.LEVELCOMPLETE) {
            font.draw(batcher, "Level complete!", 40, 20);
            font.draw(batcher, "+200 xp", 50, 40);
            font.draw(batcher, "Press space to continue", 30, 70);
        }

        batcher.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Render ball
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.circle(world.getBall().getX(), world.getBall().getY(), world.getBall().getRadius());

        // Render paddle
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(world.getPaddle().getX(), world.getPaddle().getY(), world.getPaddle().getWidth(), world.getPaddle().getHeight());

        shapeRenderer.end();
    }

    private void drawHud() {

    }

    private void drawLevelWon() {
//        drawGamePlay();
//        batcher.begin();
//        font.draw(batcher, "Level complete!", 20, 20);
//        font.draw(batcher, "+200 xp", 20, 30);
//
//        font.draw(batcher, "Press space to continue", 20, 40);
//
//        batcher.end();

    }

    private void drawGameOver() {

    }
}
