package be.simonraes.breakout.world;

import be.simonraes.breakout.actors.Ball;
import be.simonraes.breakout.actors.Paddle;
import be.simonraes.breakout.block.Block;
import be.simonraes.breakout.game.PlayerState;
import be.simonraes.breakout.level.Level;
import be.simonraes.breakout.level.Level1;
import be.simonraes.breakout.level.Level2;
import be.simonraes.breakout.powerup.*;
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


    private PlayerState playerState;

    private ArrayList<PowerUp> fallingPowerUps;
    private ArrayList<Ball> balls;


    private GameState gameState;

    public enum GameState {
        READYFORLAUNCH, // User can move paddle to choose launch location.
        RUNNING,        // Game is running.
        LEVELCOMPLETE,  // All blocks of the current level have been destroyed.
        LIFEOVER,       // Last ball hit bottom of screen, lose 1 life.
        GAMEOVER,       // Last ball hit bottom of screen with no lives left, game over.
    }

    public GameWorld() {
        playerState = new PlayerState(1, 3);
        setupGame();
    }

    private void setupGame() {
        setActors();
        setLevel();
    }

    private void setActors() {
        gameState = GameState.READYFORLAUNCH;
        fallingPowerUps = new ArrayList<PowerUp>();

        paddle = new Paddle(136 / 2 - 10, GameScreen.gameHeight - 20, PADDLE_WIDTH, PADDLE_HEIGHT);
        balls = new ArrayList<Ball>();
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
            // No more balls, take away 1 life.
            playerState.setLives(playerState.getLives() - 1);

            // Check if player has a life to play another round
            if (playerState.getLives() <= 0) {
                gameState = GameState.GAMEOVER;
            } else {
                gameState = GameState.READYFORLAUNCH;
            }
        }

        System.out.println(playerState.getLives());

        checkBallPaddleCollision();
        checkBallTargetsCollision();
        checkPaddlePowerupsCollision();

        for (Ball ball : balls) {
            ball.update(delta);
        }
    }

    /**Updates the position of falling powerUps.*/
    private void updatePowerUps(float delta) {
        for (PowerUp p : fallingPowerUps) {
            p.update(delta);
        }
    }

    /**Checks if the ball hit the paddle.*/
    private void checkBallPaddleCollision() {
        for (Ball ball : balls) {
            if (Intersector.overlaps(ball.getCircle(), paddle.getRectangle())) {
                ball.paddleCollision(paddle);
            }
        }
    }

    /**Checks if the ball hits a target block.*/
    private void checkBallTargetsCollision() {
        for (Block block : level.getBlocks()) {
            if (block.isAlive()) {
                for (Ball ball : balls) {
                    if (Intersector.overlaps(ball.getCircle(), block.getRectangle())) {
                        hitBrick(ball, block);
                    }
                }
            }
        }
    }


    private void hitBrick(Ball ball, Block block){
        if(ball!=null){
            ball.blockCollision(block);
        }
        // Hit the block and check if it released a powerup.
        if(block.isAlive()){
            PowerUp powerUp = block.hit();
            addPowerUp(powerUp);
        }

    }

    private void addPowerUp(PowerUp spawnedPowerUp) {
        if (spawnedPowerUp != null) {
            if(spawnedPowerUp.getActivation() == PowerUp.PowerUpActivation.FALLING){
                fallingPowerUps.add(spawnedPowerUp);
            } else {
                activatePowerUp(spawnedPowerUp);
            }
        }
    }

    private void checkPaddlePowerupsCollision() {
        for (PowerUp p : (ArrayList<PowerUp>) fallingPowerUps.clone()) {
            if (Intersector.overlaps(p.getCircle(), paddle.getRectangle())) {
                activatePowerUp(p);
                fallingPowerUps.remove(p);
            }
        }
    }

    private void activatePowerUp(PowerUp powerUp) {
        switch (powerUp.getPowerUpTarget()) {
            case BALL:
                for (Ball ball : balls) {
                    ball.applyPowerUp(powerUp);
                }
                break;
            case PADDLE:
                paddle.applyPowerUp(powerUp);
                break;
            case GAME:
                switch (powerUp.getPowerUpEffect()) {
                    case EXTRABALL:
                        balls.add(new Ball(paddle.getX() + (paddle.getWidth() / 2), paddle.getY(), BALL_RADIUS));
                        break;
                    case EXTRALIFE:
                        playerState.setLives(playerState.getLives() + 1);
                        break;
                    case EXPLODEBRICK:
                        for(Block block : level.getBlocks()){
                            if(Intersector.overlaps(powerUp.getCircle(), block.getRectangle())){
                                hitBrick(null, block);
                            }
                        }
                }
                break;
            default:
                break;
        }
    }

    public void launchCommand() {
        if (gameState == GameWorld.GameState.READYFORLAUNCH) {
            gameState = GameWorld.GameState.RUNNING;
            for (Ball ball : getBalls()) {
                ball.launch();
            }
        } else if (gameState == GameWorld.GameState.LEVELCOMPLETE) {
            startNextLevel();
        } else if (gameState == GameWorld.GameState.LIFEOVER) {
            restart();
        } else if (gameState == GameState.GAMEOVER) {
            fullRestart();
        }
    }


    public void restart() {
        setupGame();
    }

    public void fullRestart() {
        playerState = new PlayerState(1, 3);
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
        setActors();
        setLevel();
    }

    public GameState getGameState() {
        return gameState;
    }

    public ArrayList<PowerUp> getFallingPowerUps() {
        return fallingPowerUps;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
