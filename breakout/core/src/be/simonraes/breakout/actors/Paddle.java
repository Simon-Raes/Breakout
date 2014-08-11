package be.simonraes.breakout.actors;

import be.simonraes.breakout.powerup.Powerup;
import be.simonraes.breakout.screen.GameScreen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Simon Raes on 7/08/2014.
 */
public class Paddle {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private static int STANDARD_ACCELERATION = 500;
    private static float CHANGE_DIRECTION_DAMP = 0.5f;
    private static float STOP_MOVING_DAMP = 0.7f;
    private int width, height;


    public Paddle(float xPosition, float yPosition, int width, int height) {
        this.width = width;
        this.height = height;

        position = new Vector2(xPosition, yPosition);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
    }

    public void update(float delta) {
        // Update paddle speed
        velocity.add(acceleration.cpy().scl(delta));

        // Don't let paddle move out of the screen
        if (position.x < 0) {
            position.x = 0;
            velocity.x = 0;
        }
        if (position.x > GameScreen.gameWidth - width) {
            position.x = GameScreen.gameWidth - width;
            velocity.x = 0;
        }

        //Set paddle max speed
        if (velocity.x > 200) {
            velocity.x = 200;
        }
        if (velocity.x < -200) {
            velocity.x = -200;
        }

        // Slow down the paddle if no button is held down.
        if (acceleration.x == 0) {
            velocity.x = velocity.x * STOP_MOVING_DAMP;
        }

        if (velocity.x > 0 && acceleration.x < 0) {
            velocity.x = velocity.x * CHANGE_DIRECTION_DAMP;
        }

        if (velocity.x < 0 && acceleration.x > 0) {
            velocity.x = velocity.x * CHANGE_DIRECTION_DAMP;
        }

        // Update paddle position
        position.add(velocity.cpy().scl(delta));

    }

    public void goLeft() {
        acceleration.x = -STANDARD_ACCELERATION;
    }

    public void goRight() {
        acceleration.x = STANDARD_ACCELERATION;
    }

    public void stopMoving() {
        acceleration.x = 0;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getXVelocity() {
        return velocity.x;
    }

    public Rectangle getRectangle() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public void applyPowerUp(Powerup p) {

    }
}
