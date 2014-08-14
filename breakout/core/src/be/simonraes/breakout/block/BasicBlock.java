package be.simonraes.breakout.block;

import be.simonraes.breakout.powerup.PowerUp;
import com.badlogic.gdx.graphics.Color;

/**
 * A basic block.
 * Created by Simon Raes on 8/08/2014.
 */
public class BasicBlock extends Block {

    public BasicBlock(float xPosition, float yPosition, int width, int height) {
        super(xPosition, yPosition, width, height, Color.YELLOW);
    }

    @Override
    protected void setPowerups() {
        containedPowerups.put(PowerUp.PowerUpEffect.FLAMEBALL, 5);
        containedPowerups.put(PowerUp.PowerUpEffect.EXTRABALL, 5);
    }
}
