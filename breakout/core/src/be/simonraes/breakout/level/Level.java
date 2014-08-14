package be.simonraes.breakout.level;

import be.simonraes.breakout.block.BasicBlock;
import be.simonraes.breakout.block.Block;
import be.simonraes.breakout.block.ExplodingBlock;
import be.simonraes.breakout.block.StrongBlock;
import be.simonraes.breakout.screen.GameScreen;

import java.util.ArrayList;

/**
 * Created by Simon Raes on 8/08/2014.
 */
public abstract class Level {

    protected int[][] blockInts;
    private ArrayList<Block> blockObjects;
    private final int BLOCK_WIDTH = 10;
    private final int BLOCK_HEIGHT = 7;
    private final int BLOCK_HORIZONTAL_PADDING = 2;
    private final int BLOCK_TOP_PADDING = 15;
    private final int BLOCK_VERTICAL_PADDING = 2;

    private int numberOfBlocksInLevel;

    public void createBlocks() {
        blockObjects = new ArrayList<Block>();

        for (int y = 0; y < blockInts.length; y++) {
            for (int x = 0; x < blockInts[y].length; x++) {

                int xPos = x * BLOCK_HORIZONTAL_PADDING + getSidePadding() + x * BLOCK_WIDTH;
                int yPos = y * BLOCK_VERTICAL_PADDING + BLOCK_VERTICAL_PADDING+BLOCK_TOP_PADDING + y * BLOCK_HEIGHT;

                switch (blockInts[y][x]){
                    case 1:
                        addBasicBlock(xPos, yPos);
                        break;
                    case 2:
                        addStrongBlock(xPos, yPos);
                        break;
                    case 3:
                        addExplodingBlock(xPos,yPos);
                        break;
                }
            }
        }
    }



    private void addBasicBlock(int xPos, int yPos){

        blockObjects.add(new BasicBlock(
                xPos, yPos,
                BLOCK_WIDTH,
                BLOCK_HEIGHT));
        numberOfBlocksInLevel++;
    }

    private void addStrongBlock(int xPos, int yPos){

        blockObjects.add(new StrongBlock(
                xPos, yPos,
                BLOCK_WIDTH,
                BLOCK_HEIGHT));
        numberOfBlocksInLevel++;
    }

    private void addExplodingBlock(int xPos, int yPos) {

        blockObjects.add(new ExplodingBlock(
                xPos, yPos,
                BLOCK_WIDTH,
                BLOCK_HEIGHT));
        numberOfBlocksInLevel++;
    }

    private int getSidePadding() {
        float screenWidth = GameScreen.gameWidth;
        int numberOfBlocks = blockInts[0].length;
        int blocksWidth = numberOfBlocks * BLOCK_WIDTH + ((numberOfBlocks - 1) * BLOCK_HORIZONTAL_PADDING);

        return (int)(screenWidth - blocksWidth) / 2;
    }

    public ArrayList<Block> getBlocks() {
        if (blockObjects == null) {
            createBlocks();
        }
        return blockObjects;
    }

    public int getNumberOfBlocksInLevel() {
        return numberOfBlocksInLevel;
    }

    public int getNumberOfAliveBlocks() {
        int aliveBlocks = 0;
        for(Block b : blockObjects){
            if(b.isAlive()){
                aliveBlocks ++;
            }
        }
        return aliveBlocks;
    }

    public boolean hasAliveBlocks(){
        for(Block b : blockObjects){
            if(b.isAlive()){
                return true;
            }
        }
        return false;
    }
}
