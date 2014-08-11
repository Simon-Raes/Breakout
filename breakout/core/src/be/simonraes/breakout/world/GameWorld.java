package be.simonraes.breakout.world;

import be.simonraes.breakout.actors.Ball;
import be.simonraes.breakout.block.Block;
import be.simonraes.breakout.actors.Paddle;
import be.simonraes.breakout.game.PlayerState;
import be.simonraes.breakout.level.Level;
import be.simonraes.breakout.level.Level1;
import be.simonraes.breakout.level.Level2;
import be.simonraes.breakout.powerup.ExtraBall;
import be.simonraes.breakout.powerup.FlameBall;
import be.simonraes.breakout.powerup.Powerup;
import be.simonraes.breakout.screen.GameScreen;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;

/**
 * Controls all actors.
 * Created by Simon Raes on 7/08/2014.
 */
public class GameWorld {

    private Paddle paddle;
    private static int PADDLE_WIDTH = 20;
    private static int PADDLE_HEIGHT = 5;

    //    private Ball ball;
    private static int BALL_RADIUS = 2;

    private Level level;

    private GameState gameState;
    private PlayerState playerState;

    private ArrayList<Powerup> fallingPowerUps;
    private ArrayList<Ball> balls;

    public enum GameState {
        READYFORLAUNCH, // User can move paddle to choose launch location.
        RUNNING,        // Game is running.
        LEVELCOMPLETE,  // All blocks of the current level have been destroyed.
        LIFEOVER,       // Last ball hit bottom of screen, lose 1 life.
        GAMEOVER,       // Last ball hit bottom of screen with no lives left, game over.
    }

    public GameWorld() {
        playerState = new PlayerState();
        setupGame();
    }

    private void setupGame() {
        initObjects();
        setLevel();
    }

    private void initObjects() {
        gameState = GameState.READYFORLAUNCH;
        fallingPowerUps = new ArrayList<Powerup>();

        paddle = new Paddle(136 / 2 - 10, GameScreen.gameHeight - 20, PADDLE_WIDTH, PADDLE_HEIGHT);
        balls = new ArrayList<Ball>();
//        balls.add(new Ball(136 / 2, GameScreen.gameHeight - 20 - BALL_RADIUS, BALL_RADIUS));
    }

    private void setLevel() {
        switch (playerState.getCurrentLevel()) {
            case 1:
                level = new Level1();
                break;
            case 2:
                level = new Level2();
                break;
            default:
                level = new Level1();
                break;
        }
    }

    public void update(float delta) {
        switch (gameState) {
            case READYFORLAUNCH:
                updateReadyForLaunch(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case LIFEOVER:
                break;
        }

    }

    private void levelComplete() {
        gameState = GameState.LEVELCOMPLETE;
        playerState.setCurrentLevel(playerState.getCurrentLevel() + 1);
    }

    private void updateReadyForLaunch(float delta) {
        paddle.update(delta);

        // Powerups can still be collected during READYFORLAUNCH.
        updatePowerUps(delta);
        checkPaddlePowerupsCollision();

        if (balls.size() <= 0) {
            balls.add(new Ball(136 / 2, GameScreen.gameHeight - 20 - BALL_RADIUS, BALL_RADIUS));
        }

        for (Ball ball : balls) {
            ball.moveWithPaddle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
            ball.update(delta);
        }
    }

    private void updateRunning(float delta) {
        paddle.update(delta);

        updatePowerUps(delta);

        // Check number of alive bricks
        if (!level.hasAliveBlocks()) {
            levelComplete();
        }


        // Check if game is over
        for (Ball ball : (ArrayList<Ball>) balls.clone()) {
            if (ball.getY() - ball.getRadius() > GameScreen.gameHeight) {
//                ball.stop();
                balls.remove(ball);
            }
        }

        // Check number of alive balls
        if (balls.size() <= 0) {
            gameState = GameState.READYFORLAUNCH;

        }


        checkBallPaddleCollision();
        checkBallTargetsCollision();
        checkPaddlePowerupsCollision();

        for (Ball ball : balls) {
            ball.update(delta);
        }
    }

    private void updatePowerUps(float delta) {
        // Update fallingPowerUps
        for (Powerup p : fallingPowerUps) {
            p.update(delta);
        }
    }

    private void checkBallPaddleCollision() {
        for (Ball ball : balls) {
            if (Intersector.overlaps(ball.getCircle(), paddle.getRectangle())) {
                ball.paddleCollision(paddle);
            }
        }
    }

    private void checkBallTargetsCollision() {
        for (Block block : level.getBlocks()) {
            if (block.isAlive()) {
                for (Ball ball : balls) {
                    if (Intersector.overlaps(ball.getCircle(), block.getRectangle())) {
                        ball.blockCollision(block);
                        // Check if the hit block spawned a powerup.
                        Powerup.PowerUpEffect spawnedPowerUp = block.hit();
                        if (spawnedPowerUp != null) {
                            switch (spawnedPowerUp) {
                                case FLAMEBALL:
                                    fallingPowerUps.add(new FlameBall(block.getX() + (block.getWidth() / 2), block.getY() + block.getHeight()));
                                    break;
                                case EXTRABALL:
                                    fallingPowerUps.add(new ExtraBall(block.getX() + (block.getWidth() / 2), block.getY() + block.getHeight()));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }


    private void checkPaddlePowerupsCollision() {
        for (Powerup p : (ArrayList<Powerup>) fallingPowerUps.clone()) {
            if (Intersector.overlaps(p.getCircle(), paddle.getRectangle())) {
                activatePowerUp(p);
                fallingPowerUps.remove(p);
            }
        }
    }

    private void activatePowerUp(Powerup p) {
        switch (p.getPowerUpTarget()) {
            case BALL:
                for (Ball ball : balls) {
                    ball.applyPowerUp(p);
                }
                break;
            case PADDLE:
                paddle.applyPowerUp(p);
                break;
            case GAME:
                balls.add(new Ball(paddle.getX() + (paddle.getWidth() / 2), paddle.getY(), BALL_RADIUS));

                break;
            default:
                break;
        }
    }

    public void restart() {
        setupGame();
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Level getLevel() {
        return level;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void startNextLevel() {
        initObjects();
        setLevel();
    }

    public GameState getGameState() {
        return gameState;
    }

    public ArrayList<Powerup> getFallingPowerUps() {
        return fallingPowerUps;
    }
}
