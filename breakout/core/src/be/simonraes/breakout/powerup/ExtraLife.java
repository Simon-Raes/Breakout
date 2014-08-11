package be.simonraes.breakout.powerup;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Simon Raes on 11/08/2014.
 */
public class ExtraLife extends Powerup {

    public ExtraLife(float positionX, float positionY) {
        super(positionX, positionY, 0, 50, PowerUpTarget.GAME, PowerUpEffect.EXTRALIFE, Color.WHITE);
    }
}
