package minefield;

import mvc.*;
import java.awt.*;
import java.util.List;

public class MineFieldView extends View {
    private Cell[][] cells;
    private static final int CELL_SIZE = 25;
    private static final Color VISITED_COLOR = Color.WHITE;
    private static final Color UNVISITED_COLOR = Color.LIGHT_GRAY;
    private static final Color GOAL_COLOR = Color.GREEN;
    private static final Color PLAYER_COLOR = Color.BLUE;
    
    public MineFieldView(MineFieldModel model) {
        super(model);
        initView();
    }
    
    @Override
    public void setModel(Model newModel) {
        super.setModel(newModel);
        initView();
        repaint();
    }
    
    private void initView() {
        if (model == null) return;
        
        MineFieldModel mineModel = (MineFieldModel) model;
        int size = mineModel.getSize();
        cells = new Cell[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (model == null) return new Dimension(300, 300);
        
        MineFieldModel mineModel = (MineFieldModel) model;
        int size = mineModel.getSize();
        return new Dimension(size * CELL_SIZE, size * CELL_SIZE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (model == null) return;
        
        MineFieldModel mineModel = (MineFieldModel) model;
        int size = mineModel.getSize();
        
        // Draw the grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j].draw(g, mineModel);
            }
        }
        
        // Draw the path on top of the cells
        drawPath(g, mineModel);
    }
    
    /**
     * Draws the path the player has taken.
     */
    private void drawPath(Graphics g, MineFieldModel model) {
        List<int[]> path = model.getPath();
        if (path.size() > 1) {
            g.setColor(Color.BLACK);
            for (int i = 1; i < path.size(); i++) {
                int[] prev = path.get(i - 1);
                int[] curr = path.get(i);
                
                int x1 = prev[1] * CELL_SIZE + CELL_SIZE / 2;
                int y1 = prev[0] * CELL_SIZE + CELL_SIZE / 2;
                int x2 = curr[1] * CELL_SIZE + CELL_SIZE / 2;
                int y2 = curr[0] * CELL_SIZE + CELL_SIZE / 2;
                
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
    
    /**
     * Represents a cell in the minefield grid.
     */
    private class Cell {
        private final int row, col;
        
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public void draw(Graphics g, MineFieldModel model) {
            int x = col * CELL_SIZE;
            int y = row * CELL_SIZE;
            
            drawCellBackground(g, model, x, y);
            drawCellBorder(g, x, y);
            drawPlayer(g, model, x, y);
            drawMineCount(g, model, x, y);
        }
        
        /**
         * Draws the cell background with appropriate color.
         */
        private void drawCellBackground(Graphics g, MineFieldModel model, int x, int y) {
            // Set the cell color
            if (model.isGoal(row, col)) {
                g.setColor(GOAL_COLOR);
            } else if (model.isVisited(row, col)) {
                g.setColor(VISITED_COLOR);
            } else {
                g.setColor(UNVISITED_COLOR);
            }
            
            // Draw the cell
            g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        }
        
        /**
         * Draws the cell border.
         */
        private void drawCellBorder(Graphics g, int x, int y) {
            g.setColor(Color.BLACK);
            g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
        }
        
        /**
         * Draws the player if this cell contains the player.
         */
        private void drawPlayer(Graphics g, MineFieldModel model, int x, int y) {
            if (row == model.getPlayerRow() && col == model.getPlayerCol()) {
                g.setColor(PLAYER_COLOR);
                int padding = CELL_SIZE / 4;
                g.fillOval(x + padding, y + padding, CELL_SIZE - 2 * padding, CELL_SIZE - 2 * padding);
            }
        }
        
        /**
         * Draws the mine count for visited cells.
         */
        private void drawMineCount(Graphics g, MineFieldModel model, int x, int y) {
            if (model.isVisited(row, col)) {
                int mines = model.getNeighboringMines(row, col);
                if (mines > 0) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.drawString("" + mines, x + CELL_SIZE / 2 - 5, y + CELL_SIZE / 2 + 5);
                }
            }
        }
    }
} 