package minefield;

import mvc.*;
import java.util.*;

/**
 * Model class for the Minefield game.
 * Handles game state, player movement, and game rules.
 */
public class MineFieldModel extends Model {

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
        
        // Now calculate numbers for all non-bomb tiles
        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                if (tiles[row][col].getNumBombs() != -1) {
                    tiles[row][col].setNumBombs(countNeighboringMines(row, col));
                }
            }
        }
    }

    private int countNeighboringMines(int row, int col) {
        int count = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                // Skip the center tile and out of bounds tiles
                if ((r == row && c == col) || isOutOfBounds(r, c)) {
                    continue;
                }
                
                if (tiles[r][c].getNumBombs() == -1) {
                    count++;
                }
            }
        }
        return count;
    }

    private void makePlayer() {
        playerRow = 0;
        playerCol = 0;
        // Mark the starting position as already stepped on
        tiles[playerRow][playerCol].changeIsSteppedOn(true);
        tiles[playerRow][playerCol].setPlayerOn(true);
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

        int newRow = newPosition[0];
        int newCol = newPosition[1];
        
        if (isOutOfBounds(newRow, newCol)) {
            throw new Exception("Cannot move off the grid!");
        }
        
        // Remove player from current tile
        tiles[playerRow][playerCol].setPlayerOn(false);
        
        // Update player position
        playerRow = newRow;
        playerCol = newCol;
        
        // Update new tile
        tiles[playerRow][playerCol].changeIsSteppedOn(true);
        tiles[playerRow][playerCol].setPlayerOn(true);
        
        // Notify observers that model has changed
        setUnsavedChanges(true);
        notifySubscribers();
        
        checkGameOutcome();
    }

    private int[] updatePosition(Heading heading) {
        int newRow = playerRow;
        int newCol = playerCol;

        switch (heading) {
            case N:
                newCol--;
                break;
            case S:
                newCol++;
                break;
            case E:
                newRow++;
                break;
            case W:
                newRow--;
                break;
            case NW:
                newCol--;
                newRow--;
                break;
            case NE:
                newCol--;
                newRow++;
                break;
            case SW:
                newCol++;
                newRow--;
                break;
            case SE:
                newCol++;
                newRow++;
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
            throw new WinException("Congratulations! You reached the goal and won!");
        }
    }

    public Tile[][] getTiles() { return tiles; }

    /**
     * Custom exception class for win conditions to differentiate from errors
     */
    public static class WinException extends Exception {
        public WinException(String message) {
            super(message);
        }
    }

}