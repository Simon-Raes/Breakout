package be.simonraes.breakout.level;

import be.simonraes.breakout.actors.Block;

import java.util.ArrayList;

/**
 * Created by Simon Raes on 8/08/2014.
 */
public abstract class Level {

    protected int[][] blockInts;
    private ArrayList<Block> blockObjects;
    private final int BLOCK_WIDTH = 15;
    private final int BLOCK_HEIGHT = 10;
    private final int BLOCK_PADDING = 10;

    public void createBlocks(){
        blockObjects = new ArrayList<Block>();

        for (int y = 0; y < blockInts.length; y++) {
            for (int x = 0; x < blockInts[y].length; x++) {
                if(blockInts[y][x] == 1){
                    int xPos = x * BLOCK_PADDING+BLOCK_PADDING + x * BLOCK_WIDTH;
                    int yPos = y * BLOCK_PADDING +BLOCK_PADDING+ y * BLOCK_HEIGHT;

                    blockObjects.add(new Block(
                            xPos, yPos,
                            BLOCK_WIDTH,
                            BLOCK_HEIGHT));

                }

            }
        }
    }

    public ArrayList<Block> getBlocks() {
        if(blockObjects==null){
            createBlocks();
        }
        return blockObjects;
    }

    public int[][] getBlockInts() {
        return blockInts;
    }

}
