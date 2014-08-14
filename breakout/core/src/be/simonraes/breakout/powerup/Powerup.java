package be.simonraes.breakout.powerup;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Simon Raes on 11/08/2014.
 */
public abstract class PowerUp {
    protected Vector2 position;
    protected Vector2 velocity;

    protected int radius; // Size of the falling powerup item.
    protected int effectDuration;

    private Color texture; //todo: replace with actual texture instead of color

    protected PowerUpActivation activation;
    public enum PowerUpActivation{
        FALLING, // The powerup will fall down out of the block and become activated when the paddle catches it.
        INSTANT  // The powerup will activate as soon as it is created.
    }

    protected PowerUpTarget powerUpTarget;
    public enum PowerUpTarget {
        BALL,    // Gives a special effect to the ball.
        PADDLE,  // Gives a special effect to the paddle.
        GAME     // Does something else.
    }

    protected PowerUpEffect powerUpEffect;
    public enum PowerUpEffect {
        FLAMEBALL,    // Ball will no longer change direction after hitting a brick. Lasts 5 seconds.
        EXTRABALL,    // An extra ball will be launched.
        EXTRALIFE,    // Adds 1 life.
        EXPLODEBRICK, // The brick explodes, damaging nearby bricks.
    }

    public PowerUp(float positionX, float positionY, float velocityX, float velocitY, PowerUpActivation activation, PowerUpTarget target, PowerUpEffect effect, Color texture) {
        this.position = new Vector2(positionX, positionY);
        velocity = new Vector2(velocityX, velocitY);
        this.activation = activation;
        this.powerUpTarget = target;
        this.powerUpEffect = effect;
        this.texture = texture;

        this.effectDuration = -1; // Set the default duration to be infinite.
        radius = 4;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getCircle() {
        return new Circle(position.x, position.y, radius);
    }

    public PowerUpActivation getActivation() {
        return activation;
    }

    public PowerUpTarget getPowerUpTarget() {
        return powerUpTarget;
    }

    public PowerUpEffect getPowerUpEffect() {
        return powerUpEffect;
    }

    public int getEffectDuration(){
        return effectDuration;
    }

    public Color getTexture(){
        return texture;
    }

}
