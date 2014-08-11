package be.simonraes.breakout.game;

/**
 * Created by Simon Raes on 10/08/2014.
 */
public class PlayerState {

    private int currentLevel;
    private int lives;

    public PlayerState(int level, int lives) {
        this.currentLevel = level;
        this.lives = lives;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
