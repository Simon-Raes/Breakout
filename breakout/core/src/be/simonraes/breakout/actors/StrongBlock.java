package be.simonraes.breakout.actors;

/**
 * A basic block that takes 3 hits to destroy.
 * Created by Simon Raes on 10/08/2014.
 */
public class StrongBlock extends Block {
    public StrongBlock(float xPosition, float yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
        hitPoints = 3;
    }
}
