package be.simonraes.breakout.actors;

import be.simonraes.breakout.powerup.Powerup;
import be.simonraes.breakout.screen.GameScreen;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Simon Raes on 7/08/2014.
 */
public class Ball {

    private Vector2 previousPosition; // Used to calculated ball angle.
    private Vector2 position;
    private Vector2 velocity;


    private final int BALL_SPEED = 120;
    private final int LAUNCH_ANGLE = -60;
    private final int MAX_X_DISTANCE_AFTER_PADDLE_HIT = 400;

    private HashMap<Powerup.PowerUpEffect, Float> activeEffects;


    private int radius;

    public Ball(float xPosition, float yPosition, int radius) {
        this.radius = radius;
        previousPosition = new Vector2(0, 0);
        position = new Vector2(xPosition, yPosition);
        velocity = new Vector2(0, 0);

        activeEffects = new HashMap<Powerup.PowerUpEffect, Float>();
    }

    public void update(float delta) {
        previousPosition.x = position.x;
        previousPosition.y = position.y;

        // Don't let ball move out of the screen
        if (position.x < 0 + radius) {
            position.x = 0 + radius;
            velocity.x = -velocity.x;
        }
        if (position.x + radius > GameScreen.gameWidth) {
            position.x = GameScreen.gameWidth - radius;
            velocity.x = -velocity.x;
        }
        if (position.y < 0 + radius) {
            position.y = 0 + radius;
            velocity.y = -velocity.y;
        }

        // Update effects
        for (Map.Entry<Powerup.PowerUpEffect, Float> entry : activeEffects.entrySet()) {
            if (entry.getValue() <= 0) {
                activeEffects.remove(entry.getKey());
            } else {
                entry.setValue(entry.getValue() - delta);
            }
        }

        // Update ball position
        position.add(velocity.cpy().scl(delta));
    }


    public void launch() {
        double x = BALL_SPEED * Math.sin(LAUNCH_ANGLE);
        double y = BALL_SPEED * Math.sin(90 - LAUNCH_ANGLE);

        velocity.x = (float) x;
        velocity.y = (float) y;
    }

    /**
     * Redirects the ball based on where it hit the paddle.
     */
    public void paddleCollision(Paddle paddle) {

        if (position.x < paddle.getX()) {
            position.x = paddle.getX() - radius - 1;
            reverseXVelocity();
        } else if (position.x > paddle.getX() + paddle.getWidth()) {
            position.x = paddle.getX() + paddle.getWidth() + radius + 1;
            reverseXVelocity();
        } else if (position.y > paddle.getY() + paddle.getHeight()) {

        } else {
            float difference = position.x - (paddle.getX() + (paddle.getWidth() / 2));
            float factor = difference / paddle.getWidth() / 2;
            float finalDistance = factor * MAX_X_DISTANCE_AFTER_PADDLE_HIT;
            float destinationX = finalDistance + position.x;

            float destinationY = position.y - 40;

            // Send the ball to that position, scale it to the intended BALL_SPEED.
            velocity.set(destinationX - position.x, destinationY - position.y).nor().scl(BALL_SPEED);
        }
    }

    public void blockCollision(Block block) {

        // Don't change direction if flameball powerup is active.
        if (!activeEffects.containsKey(Powerup.PowerUpEffect.FLAMEBALL)) {

            float brickX = block.getX();
            float brickY = block.getY();
            int brickWidth = block.getWidth();
            int brickHeight = block.getHeight();

            if (position.y < brickY) {
                reverseYVelocity();
                position.y = brickY - radius - 1;
            } else if (position.y > brickY + brickHeight) {
                reverseYVelocity();
                position.y = brickY + brickHeight + radius + 1;
            } else {
                if (position.x < brickX) {
                    reverseXVelocity();
                    position.x = brickX - radius - 1;
                } else if (position.x > brickX + brickWidth) {
                    reverseXVelocity();
                    position.x = brickX + brickWidth + radius + 1;
                }
            }
        }
    }

    private void reverseXVelocity() {
        velocity.x = -velocity.x;
    }

    private void reverseYVelocity() {
        velocity.y = -velocity.y;
    }

    public void stop() {
        velocity.x = 0;
        velocity.y = 0;
    }

    /**
     * Moves the ball along with the paddle during READYFORLAUNCH gamestate.
     * Lets the user choose the start position for the ball launch.
     */
    public void moveWithPaddle(float paddleX, float paddleY, int paddleWidth, int paddleHeight) {
        position.x = paddleX + (paddleWidth / 2);
        position.y = paddleY - radius;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getCircle() {
        return new Circle(position.x, position.y, radius);
    }

    public HashMap<Powerup.PowerUpEffect, Float> getActiveEffects() {
        return activeEffects;
    }

    public void applyPowerUp(Powerup p) {
        activeEffects.put(p.getPowerUpEffect(), (float) p.getEffectDuration());
    }
}
