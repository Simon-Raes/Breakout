package be.simonraes.breakout.powerup;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Simon Raes on 11/08/2014.
 */
public class ExtraBall extends PowerUp {
    public ExtraBall(float positionX, float positionY) {
        super(positionX, positionY, 0, 50, PowerUpActivation.FALLING, PowerUpTarget.GAME, PowerUpEffect.EXTRABALL, Color.PURPLE);
    }
}
