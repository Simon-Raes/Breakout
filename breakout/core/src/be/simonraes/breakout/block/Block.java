package be.simonraes.breakout.block;

import be.simonraes.breakout.powerup.*;
import com.badlogic.gdx.graphics.Color;
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
    private Color texture;

    /**
     * Powerups that can spawn when this block is destroyed. (BlockType, SpawnChance)
     */
    protected HashMap<PowerUp.PowerUpEffect, Integer> containedPowerups;

    public Block(float xPosition, float yPosition, int width, int height, Color texture) {
        position = new Vector2(xPosition, yPosition);
        this.width = width;
        this.height = height;
        this.texture = texture;

        isAlive = true;
        containedPowerups = new HashMap<PowerUp.PowerUpEffect, Integer>();

        hitPoints = 1;

        setPowerups();
    }

    protected abstract void setPowerups();

    public PowerUp hit() {
        hitPoints--;
        if (hitPoints <= 0) {
            isAlive = false;
            // Block has been destroyed, check if a power up should be created.
            return getSpawnedPowerUp();
        }
        return null;
    }

    private PowerUp getSpawnedPowerUp() {
        Random random = new Random();
        int randomValue = random.nextInt(100) + 1;
        for (Map.Entry<PowerUp.PowerUpEffect, Integer> entry : containedPowerups.entrySet()) {

            randomValue -= entry.getValue();
            if (randomValue <= 0) {

                switch (entry.getKey()) {
                    case FLAMEBALL:
                        return new FlameBall(getX() + (getWidth() / 2), getY() + getHeight());
                    case EXTRABALL:
                        return new ExtraBall(getX() + (getWidth() / 2), getY() + getHeight());
                    case EXTRALIFE:
                        return new ExtraLife(getX() + (getWidth() / 2), getY() + getHeight());
                    case EXPLODEBRICK:
                        return new Explosion(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
                    default:
                        return null;
                }
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

    public Color getTexture(){
        return texture;
    }
}
