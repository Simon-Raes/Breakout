package be.simonraes.breakout.powerup;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Simon Raes on 11/08/2014.
 */
public class FlameBall extends Powerup {


    public FlameBall(float positionX, float positionY) {
        super(positionX, positionY, 0, 40, PowerUpTarget.BALL, PowerUpEffect.FLAMEBALL, Color.ORANGE);
        effectDuration = 5;
    }


}
