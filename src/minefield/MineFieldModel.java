package minefield;

import mvc.*;
import java.util.*;
import java.io.Serializable;

/**
 * Model class for the Minefield game.
 * Handles game state, player movement, and game rules.
 */
public class MineFieldModel extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final int DEFAULT_SIZE = 20;
    public static final int DEFAULT_PERCENT_MINED = 5;

    private final int size;
    private final boolean[][] mines;
    private final boolean[][] visited;
    private int playerRow;
    private int playerCol;
    private boolean gameOver;
    private boolean won;
    private final List<int[]> path;
    
    public MineFieldModel() {
        this(DEFAULT_SIZE);
    }
    
    public MineFieldModel(int size) {
        this.size = size;
        this.mines = new boolean[size][size];
        this.visited = new boolean[size][size];
        this.path = new ArrayList<>();
        
        initializeMines();
        initializePlayer();
    }
    
    private void initializeMines() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextInt(100) < DEFAULT_PERCENT_MINED) {
                    if ((i != 0 || j != 0) && (i != size - 1 || j != size - 1)) {
                        mines[i][j] = true;
                    }
                }
            }
        }
    }
    
    private void initializePlayer() {
        playerRow = 0;
        playerCol = 0;
        visited[playerRow][playerCol] = true;
        path.add(new int[]{playerRow, playerCol});
        gameOver = false;
        won = false;
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean isVisited(int row, int col) {
        return visited[row][col];
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isWon() {
        return won;
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
    
    public List<int[]> getPath() {
        return path;
    }

    public int getNeighboringMines(int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(size - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(size - 1, col + 1); j++) {
                if (mines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public void move(Heading heading) throws Exception {
        if (gameOver) {
            throw new Exception("Game is already over!");
        }
        
        int[] newPosition = updatePositionForHeading(heading);
        int newRow = newPosition[0];
        int newCol = newPosition[1];
        
        if (isOutOfBounds(newRow, newCol)) {
            throw new Exception("Cannot move off the grid!");
        }
        
        playerRow = newRow;
        playerCol = newCol;
        visited[playerRow][playerCol] = true;
        path.add(new int[]{playerRow, playerCol});
        
        checkGameOutcome();
        
        changed();
    }
    
    private int[] updatePositionForHeading(Heading heading) {
        int newRow = playerRow;
        int newCol = playerCol;
        
        switch (heading) {
            case NORTH:
                newRow--;
                break;
            case SOUTH:
                newRow++;
                break;
            case EAST:
                newCol++;
                break;
            case WEST:
                newCol--;
                break;
            case NORTHWEST:
                newRow--;
                newCol--;
                break;
            case NORTHEAST:
                newRow--;
                newCol++;
                break;
            case SOUTHWEST:
                newRow++;
                newCol--;
                break;
            case SOUTHEAST:
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
        if (mines[playerRow][playerCol]) {
            gameOver = true;
            throw new Exception("you stepped on a mine and died");
        }
        
        if (isGoal(playerRow, playerCol)) {
            gameOver = true;
            won = true;
            throw new Exception("you reached the goal nice");
        }
    }
} 