package be.simonraes.breakout.actors;

import be.simonraes.breakout.powerup.Powerup;

/**
 * A basic block.
 * Created by Simon Raes on 8/08/2014.
 */
public class BasicBlock extends Block {

    public BasicBlock(float xPosition, float yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    @Override
    protected void setPowerups() {
        containedPowerups.put(Powerup.PowerUpEffect.FLAMEBALL, 10);
    }


}
