package be.simonraes.breakout.block;

import be.simonraes.breakout.powerup.Powerup;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Simon Raes on 10/08/2014.
 */
public abstract class Block {
    protected Vector2 position;
    protected int width, height;
    protected int hitPoints;

    protected boolean isAlive;

    /**
     * Powerups that can spawn when this block is destroyed. (BlockType, SpawnChance)
     */
    protected HashMap<Powerup.PowerUpEffect, Integer> containedPowerups;

    public Block(float xPosition, float yPosition, int width, int height) {
        position = new Vector2(xPosition, yPosition);
        this.width = width;
        this.height = height;
        isAlive = true;
        containedPowerups = new HashMap<Powerup.PowerUpEffect, Integer>();

        hitPoints = 1;

        setPowerups();
    }

    protected abstract void setPowerups();

    public Powerup.PowerUpEffect hit() {
        hitPoints--;
        if (hitPoints <= 0) {
            isAlive = false;
            // Block has been destroyed, check if a power up should be created.
            return getSpawnedPowerUp();
        }
        return null;
    }

    private Powerup.PowerUpEffect getSpawnedPowerUp() {
        Random random = new Random();
        int randomValue = random.nextInt(100) + 1;
        for (Map.Entry<Powerup.PowerUpEffect, Integer> entry : containedPowerups.entrySet()) {

            randomValue -= entry.getValue();
            if (randomValue <= 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Rectangle getRectangle() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
