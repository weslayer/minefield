package minefield;

import javax.swing.*;
import java.awt.*;

public class Tile {

    private int x;
    private int y;
    private int numBombs;
    private boolean isSteppedOn;
    private boolean isGoal;
    private boolean playerOn;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        isSteppedOn = false;
        isGoal = false;
        playerOn = false;
        //this.setSize(15, 15);
    }

    public Tile() {
        x = 0;
        y = 0;
        isSteppedOn = false;
        isGoal = false;
        playerOn = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void changeIsSteppedOn(boolean b) { isSteppedOn = b; }
    public boolean isSteppedOn() { return isSteppedOn; }
    // returns -1 if it is a bomb
    public int getNumBombs() { return numBombs; }
    public void setNumBombs(int num) { numBombs = num; }
    public boolean isGoal() { return isGoal; }
    public void setIsGoal() { isGoal = true; }
    public boolean isPlayerOn() { return playerOn; }
    public void setPlayerOn(boolean b) { playerOn = b; }
   /* public void colorTile() {
        if(isGoal) {
            if(isSteppedOn) this.setBackground(Color.LIGHT_GRAY);
            else this.setBackground(Color.GRAY);
            this.setBorder(BorderFactory.createLineBorder(Color.GREEN,1));
        }
        else if(playerOn) {
            this.setBackground(Color.LIGHT_GRAY);
            this.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
        }
        else if(isSteppedOn) {
            this.setBackground(Color.LIGHT_GRAY);
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        }
        else {
            this.setBackground(Color.GRAY);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        }
    }*/

    public String toString() {
        if(isSteppedOn) { return ""+numBombs; }
        else { return "?"; }
    }

}
