package be.simonraes.breakout.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Simon Raes on 10/08/2014.
 */
public abstract class Block {
    protected Vector2 position;
    protected int width, height;
    protected int hitPoints;

    protected boolean isAlive;

    public Block(float xPosition, float yPosition, int width, int height){
        position = new Vector2(xPosition, yPosition);
        this.width = width;
        this.height = height;
        isAlive = true;

        hitPoints = 1;
    }

    public void hit(){
        hitPoints--;
        if(hitPoints<=0){
            isAlive = false;
        }
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
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
