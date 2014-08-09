package be.simonraes.breakout.world;

import be.simonraes.breakout.actors.Ball;
import be.simonraes.breakout.actors.Block;
import be.simonraes.breakout.actors.Paddle;
import be.simonraes.breakout.level.Level;
import be.simonraes.breakout.level.Level1;
import be.simonraes.breakout.screen.GameScreen;
import com.badlogic.gdx.math.Intersector;

/**
 * Controls all actors.
 * Created by Simon Raes on 7/08/2014.
 */
public class GameWorld {

    private Paddle paddle;
    private static int PADDLE_WIDTH = 20;
    private static int PADDLE_HEIGHT = 5;

    private Ball ball;
    private static int BALL_RADIUS = 3;

    private Level level;

    private GameState gameState;

    public enum GameState {
        RUNNING, GAMEOVER
    }

    public GameWorld() {
        startGame();
    }

    private void startGame() {
        gameState = GameState.RUNNING;

        initObjects();
    }

    private void initObjects() {
        paddle = new Paddle(136 / 2 - 10, GameScreen.gameHeight - 20, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(136 / 2, GameScreen.gameHeight - 20 - BALL_RADIUS, BALL_RADIUS);

        level = new Level1();
    }

    public void update(float delta) {
        if (gameState == GameState.RUNNING) {
            updateRunning(delta);


            // Check if game is over
            if (ball.getY() - ball.getRadius() > GameScreen.gameHeight) {
                gameState = GameState.GAMEOVER;
                ball.stop();
            }
        }


        if (gameState == GameState.GAMEOVER) {
            // No update, show gameover Message
        }

    }

    private void updateRunning(float delta) {
        paddle.update(delta);

        checkBallPaddleCollision();
        checkBallTargetsCollision();

        ball.update(delta);
    }

//    private void checkBallPaddleCollision() {
//        //Check collision between side of paddle and ball
//        if (((ball.getX() + ball.getRadius() > paddle.getX() && ball.getX() < paddle.getX())
//                ||
//                (ball.getX() - ball.getRadius() < paddle.getX() + paddle.getWidth() && ball.getX() > paddle.getX() + paddle.getWidth()))
//                &&
//                ball.getY() > paddle.getY() &&
//                ball.getY() < paddle.getY() + paddle.getHeight()) {
//            ball.paddleSideCollision(ball.getX(), paddle.getX(), paddle.getWidth());
//        } else if (ball.getY() + ball.getRadius() > paddle.getY() &&
//                // Check collision between top of paddle and the ball
//
//                ball.getY() - ball.getRadius() < paddle.getY() + paddle.getHeight() &&
//                ball.getX() > paddle.getX() &&
//                ball.getX() < paddle.getX() + paddle.getWidth()) {
//            ball.paddleCollision(ball.getX(), paddle.getX() + (paddle.getWidth() / 2), paddle.getWidth(), paddle.getXVelocity());
//        }
//    }


    private void checkBallPaddleCollision() {
//        //Check collision between side of paddle and ball
//        if(ball.getXPosition() > paddle.getXPosition() && ball.getXPosition() < paddle.getXPosition() + paddle.getWidth()
//                &&ball.getYPosition() > paddle.getYPosition() && ball.getYPosition() < paddle.getYPosition() + paddle.getHeight()){
//            ball.paddleSideCollision();
//        }

//        if (((ball.getX() + ball.getRadius() > paddle.getX() && ball.getX() < paddle.getX())
//                ||
//                (ball.getX() - ball.getRadius() < paddle.getX() + paddle.getWidth() &&
//                        ball.getX() > paddle.getX() + paddle.getWidth()))
//                &&
//                ball.getY() > paddle.getY() &&
//                ball.getY() < paddle.getY() + paddle.getHeight()
//                ) {
//            ball.paddleSideCollision(ball.getX(), paddle.getX(), paddle.getWidth());
//        }
//
//        // Check collision between top of paddle and the ball
//        if (ball.getY() + ball.getRadius() > paddle.getY() &&
//                ball.getY() + ball.getRadius() < paddle.getY() + paddle.getHeight() &&
//                ball.getX() > paddle.getX() &&
//                ball.getX() < paddle.getX() + paddle.getWidth()) {
//
//            ball.paddleCollision(ball.getX(), paddle.getX() + (paddle.getWidth() / 2), paddle.getWidth(), paddle.getXVelocity());
//        }


        if(Intersector.overlaps(ball.getCircle(), paddle.getRectangle())){
            ball.collision(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        }


//        if (ball.getX() + ball.getRadius() > paddle.getX()
//                && ball.getX() - ball.getRadius() < paddle.getX() + paddle.getWidth()
//                && ball.getY() + ball.getRadius() > paddle.getY()
//                && ball.getY() - ball.getRadius() < paddle.getY() + paddle.getHeight()) {
//            ball.collision(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
//        }
//
//        //Check distance between ball en paddle corners
//        double distanceTopLeft = Math.sqrt(Math.pow(ball.getX() - paddle.getX(), 2) + Math.pow(ball.getY() - paddle.getY(), 2));
//        double distanceTopRight = Math.sqrt(Math.pow(ball.getX() - paddle.getX() + paddle.getWidth(), 2) + Math.pow(ball.getY() - paddle.getY(), 2));
//        double distanceBottomLeft = Math.sqrt(Math.pow(ball.getX() - paddle.getX(), 2) + Math.pow(ball.getY() - paddle.getY() + paddle.getHeight(), 2));
//        double distanceBottomRight = Math.sqrt(Math.pow(ball.getX() - paddle.getX() + paddle.getWidth(), 2) + Math.pow(ball.getY() - paddle.getY() + paddle.getHeight(), 2));
//
//        if (distanceTopLeft < ball.getRadius()
//                || distanceTopRight < ball.getRadius()
//                || distanceBottomLeft < ball.getRadius()
//                || distanceBottomRight < ball.getRadius()
//                ) {
//            ball.collision(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
//        }


    }

    private void checkBallTargetsCollision() {
        for (Block block : level.getBlocks()) {
            if (block.isAlive()) {

                if(Intersector.overlaps(ball.getCircle(), block.getRectangle())){
                    System.out.println("block overlap");
                    ball.collision(block.getX(), block.getY(), block.getWidth(), block.getHeight());
                    block.destroy();
                }

//                if (ball.getX() + ball.getRadius() > block.getX()
//                        && ball.getX() - ball.getRadius() < block.getX() + block.getWidth()
//                        && ball.getY() + ball.getRadius() > block.getY()
//                        && ball.getY() - ball.getRadius() < block.getY() + block.getHeight()) {
//                    ball.collision(block.getX(), block.getY(), block.getWidth(), block.getHeight());
//                    block.destroy();
//                }


                //Check distance between ball en block corners
//                double distanceTopLeft = Math.sqrt(Math.pow(ball.getX() - block.getX(), 2) + Math.pow(ball.getY() - block.getY(), 2));
//                double distanceTopRight = Math.sqrt(Math.pow(ball.getX() - (block.getX() + block.getWidth()), 2) + Math.pow(ball.getY() - block.getY(), 2));
//                double distanceBottomLeft = Math.sqrt(Math.pow(ball.getX() - block.getX(), 2) + Math.pow(ball.getY() - (block.getY() + block.getHeight()), 2));
//                double distanceBottomRight = Math.sqrt(Math.pow(ball.getX() - (block.getX() + block.getWidth()), 2) + Math.pow(ball.getY() - (block.getY() + block.getHeight()), 2));
//
//                if (distanceTopLeft < ball.getRadius()
//                        || distanceTopRight < ball.getRadius()
//                        || distanceBottomLeft < ball.getRadius()
//                        || distanceBottomRight < ball.getRadius()
//                        ) {
//                    System.out.println("distanceTopLeft "+distanceTopLeft+ " distancetopright "+distanceTopRight
//                    +"distancebottomleft "+distanceBottomLeft+ "distancebottomrght "+distanceBottomRight);
//                    ball.collision(block.getX(), block.getY(), block.getWidth(), block.getHeight());
//                    block.destroy();
//
//                }

//                if(ball.getX() + ball.getRadius() > block.getX()
//                        && ball.getX() - ball.getRadius() < block.getX() + block.getWidth()
//                        && ball.getY() + ball.getRadius() > block.getY()
//                        &&ball.getY() - ball.getRadius() < block.getY()+block.getHeight()){
//                    block.destroy();
////                    ball.collision(block.getX(), block.getY(), block.getWidth(), block.getHeight());
//                }
//                if (((ball.getX() + ball.getRadius() > block.getX() && ball.getX() < block.getX())
//                        ||
//                        (ball.getX() - ball.getRadius() < block.getX() + block.getWidth() && ball.getX() > block.getX() + block.getWidth()))
//                        &&
//                        ball.getY() > block.getY() &&
//                        ball.getY() < block.getY() + block.getHeight()) {
//                    block.destroy();
//                    ball.blockSideCollision();
//
//                } else if (ball.getY() + ball.getRadius() > block.getY() &&
//
//
//                        ball.getY() - ball.getRadius() < block.getY() + block.getHeight() &&
//                        ball.getX() > block.getX() &&
//                        ball.getX() < block.getX() + block.getWidth()) {
//                    block.destroy();
//                    ball.blockTopOrBottomCollision();
//                }


            }
        }
    }

    public void restart() {
        startGame();
    }


    public Paddle getPaddle() {
        return paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public Level getLevel() {
        return level;
    }

    public GameState getGameState() {
        return gameState;
    }
}
