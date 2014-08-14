package be.simonraes.breakout.powerup;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Simon Raes on 14/08/2014.
 */
public class Explosion extends PowerUp {

    public Explosion(float positionX, float positionY) {
        super(positionX, positionY, 0, 40, PowerUpActivation.INSTANT, PowerUpTarget.GAME, PowerUpEffect.EXPLODEBRICK, Color.RED);
        radius = 15; // Explosion radius.
    }

}
