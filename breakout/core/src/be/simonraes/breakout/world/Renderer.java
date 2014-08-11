package be.simonraes.breakout.world;

import be.simonraes.breakout.actors.Ball;
import be.simonraes.breakout.actors.Paddle;
import be.simonraes.breakout.block.BasicBlock;
import be.simonraes.breakout.block.Block;
import be.simonraes.breakout.block.StrongBlock;
import be.simonraes.breakout.powerup.Powerup;
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

        if (world.getGameState() == GameWorld.GameState.LIFEOVER) {
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
        } else if (world.getGameState() == GameWorld.GameState.GAMEOVER){
            font.draw(batcher, "GAME OVER!", 40, 20);
            font.draw(batcher, "Press space to restart", 30, 70);
        }

            batcher.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Render powerups
        for (Powerup p : world.getFallingPowerUps()) {
            switch (p.getPowerUpEffect()) {
                case FLAMEBALL:
                    shapeRenderer.setColor(Color.ORANGE);
                    break;
                case EXTRABALL:
                    shapeRenderer.setColor(Color.PURPLE);
                    break;
                default:
                    shapeRenderer.setColor(Color.ORANGE);
                    break;
            }
            shapeRenderer.circle(p.getPosition().x, p.getPosition().y, p.getRadius());
        }

        // Render ball
        for (Ball ball : world.getBalls()) {
            if (ball.getActiveEffects().containsKey(Powerup.PowerUpEffect.FLAMEBALL)) {
                shapeRenderer.setColor(Color.ORANGE);
            } else {
                shapeRenderer.setColor(Color.CYAN);
            }
            shapeRenderer.circle(ball.getX(), ball.getY(), ball.getRadius());
        }

        // Render paddle
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(world.getPaddle().getX(), world.getPaddle().getY(), world.getPaddle().getWidth(), world.getPaddle().getHeight());

        // Render lives
        int lives = world.getPlayerState().getLives() - 1;
        shapeRenderer.setColor(Color.CYAN);

        for(int i = 0;i<lives;i++){
            shapeRenderer.circle(world.getPaddle().getX() + 3 + (i*5), world.getPaddle().getY() + (world.getPaddle().getHeight()/2), 2);
        }

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
