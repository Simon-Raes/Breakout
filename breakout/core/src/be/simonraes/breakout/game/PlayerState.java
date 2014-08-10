package be.simonraes.breakout.game;

/**
 * Created by Simon Raes on 10/08/2014.
 */
public class PlayerState {

    private int currentLevel;

    public PlayerState(){
        currentLevel = 1;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
