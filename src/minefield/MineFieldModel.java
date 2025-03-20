package minefield;

import mvc.*;
import java.util.*;
import java.io.Serializable;

/**
 * Model class for the Minefield game.
 * Handles game state, player movement, and game rules.
 */
public class MineFieldModel extends Model implements Serializable {

    public static final int DEFAULT_SIZE = 20;
    public static final int DEFAULT_PERCENT_MINED = 5;

    private final int size;
    private Tile[][] tiles;
    private int playerRow;
    private int playerCol;
    private boolean gameOver;

    public MineFieldModel() {
        this(DEFAULT_SIZE);
    }

    public MineFieldModel(int size) {
        this.size = size;
        gameOver = false;
        tiles = new Tile[size][size];
        for(int i = 0; i < size; i++) {
           for(int j = 0; j < size; j++) {
               tiles[j][i] = new Tile(j,i);
           }
        }
        makeBombs();
        makePlayer();
    }

    private void makeBombs() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextInt(100) < DEFAULT_PERCENT_MINED) {
                    if ((i != 0 || j != 0) && (i != size - 1 || j != size - 1)) {
                        tiles[j][i].setNumBombs(-1);
                    }
                }
            }
        }
    }

    private void makePlayer() {
        playerRow = 0;
        playerCol = 0;
    }

    public int getSize() {
        return size;
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public boolean isGoal(int row, int col) {
        return row == size - 1 && col == size - 1;
    }

    public int getNeighboringMines(int y, int x) {
        if(tiles[x][y].getNumBombs() == -1) return -1;
        int count = 0;
        for(int i = x; i < 3; i++) {
            for(int j = y; i < 3; i++) {
                if(!isOutOfBounds(i,j) && tiles[i][j].getNumBombs() == -1)
                    count++;
            }
        }
        return count;
    }

    public void move(Heading heading) throws Exception {
        if (gameOver) {
            throw new Exception("Please make a new game to continue. Game over.");
        }

        int[] newPosition = updatePosition(heading);
        tiles[playerRow][playerCol].setPlayerOn(false);
        playerRow = newPosition[0];
        playerCol = newPosition[1];
        if (isOutOfBounds(playerRow, playerCol)) {
            throw new Exception("Cannot move off the grid!");
        }
        tiles[playerRow][playerCol].changeIsSteppedOn(true);
        tiles[playerRow][playerCol].setPlayerOn(true);
        checkGameOutcome();
    }

    private int[] updatePosition(Heading heading) {
        int newRow = playerRow;
        int newCol = playerCol;

        switch (heading) {
            case N:
                newRow--;
                break;
            case S:
                newRow++;
                break;
            case E:
                newCol++;
                break;
            case W:
                newCol--;
                break;
            case NW:
                newRow--;
                newCol--;
                break;
            case NE:
                newRow--;
                newCol++;
                break;
            case SW:
                newRow++;
                newCol--;
                break;
            case SE:
                newRow++;
                newCol++;
                break;
        }

        return new int[]{newRow, newCol};
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= size || col < 0 || col >= size;
    }

    private void checkGameOutcome() throws Exception {
        if (tiles[playerRow][playerCol].getNumBombs() == -1) {
            gameOver = true;
            throw new Exception("You stepped on a mine! Game over!");
        }

        if (isGoal(playerRow, playerCol)) {
            gameOver = true;
            throw new Exception("You reached the goal and won!");
        }
    }

    public Tile[][] getTiles() { return tiles; }

}