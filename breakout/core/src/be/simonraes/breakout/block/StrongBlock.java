package be.simonraes.breakout.block;

import be.simonraes.breakout.powerup.PowerUp;
import com.badlogic.gdx.graphics.Color;

/**
 * A basic block that takes 3 hits to destroy.
 * Created by Simon Raes on 10/08/2014.
 */
public class StrongBlock extends Block {
    public StrongBlock(float xPosition, float yPosition, int width, int height) {
        super(xPosition, yPosition, width, height, Color.BLACK);
        hitPoints = 3;
    }

    @Override
    protected void setPowerups() {
        containedPowerups.put(PowerUp.PowerUpEffect.FLAMEBALL, 10);
        containedPowerups.put(PowerUp.PowerUpEffect.EXTRALIFE, 3);
    }

    @Override
    public Color getTexture() {
        switch (getHitPoints()) {
            case 3:
                return Color.BLACK;
            case 2:
                return Color.DARK_GRAY;
            case 1:
                return Color.LIGHT_GRAY;
        }
        return super.getTexture();
    }
}
