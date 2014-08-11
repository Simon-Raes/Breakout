package be.simonraes.breakout.powerup;

/**
 * Created by Simon Raes on 11/08/2014.
 */
public class FlameBall extends Powerup {


    public FlameBall(float positionX, float positionY) {
        super(positionX, positionY, 0, 40, PowerUpTarget.BALL, PowerUpEffect.FLAMEBALL, 5);
    }


}
