package be.simonraes.breakout.block;

import be.simonraes.breakout.powerup.PowerUp;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Simon Raes on 14/08/2014.
 */
public class ExplodingBlock extends Block {

    public ExplodingBlock(float xPosition, float yPosition, int width, int height) {
        super(xPosition, yPosition, width, height, Color.RED);

    }

    @Override
    protected void setPowerups() {
        containedPowerups.put(PowerUp.PowerUpEffect.EXPLODEBRICK, 100);
    }
}
