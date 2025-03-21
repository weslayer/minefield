package minefield;

import mvc.*;

import java.awt.*;

public class MineFieldView extends View {

    private static final int CELL_SIZE = 15;
    private int fieldSize;

    public MineFieldView(MineFieldModel model) {
        super(model);
        this.setLayout(new GridLayout(20,20));
        fieldSize = model.getSize() * CELL_SIZE;
        setPreferredSize(new Dimension(fieldSize, fieldSize));
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
        // Nothing else needed since we're drawing directly in paintComponent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model == null) return;
        
        MineFieldModel mineModel = (MineFieldModel) model;
        int size = mineModel.getSize();
        Tile[][] tiles = mineModel.getTiles();

        // Draw the grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i*CELL_SIZE;
                int y = j*CELL_SIZE;
                
                // Fill background based on tile state
                if(tiles[i][j].isPlayerOn()) {
                    // Draw normal tile background first
                    if(tiles[i][j].isSteppedOn()) {
                        g.setColor(new Color(245, 245, 245));  // Almost white
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(new Color(200, 200, 200));  // Gray
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                    
                    // Draw red border to indicate player position
                    g.setColor(Color.RED);
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                    
                    // Show number of neighboring bombs if this is stepped on
                    if (tiles[i][j].isSteppedOn()) {
                        drawBombNumber(g, tiles[i][j].getNumBombs(), x, y);
                    }
                }
                else if(tiles[i][j].isGoal()) {
                    if(tiles[i][j].isSteppedOn()){
                        g.setColor(Color.GREEN);
                        g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(x, y,CELL_SIZE,CELL_SIZE);
                    }
                    else{
                        g.setColor(Color.GREEN);
                        g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(x, y,CELL_SIZE,CELL_SIZE);
                    }
                    g.setColor(new Color(0, 100, 0));     // Dark green
                    g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                    
                    // Also show number of neighboring bombs if this is stepped on
                    if (tiles[i][j].isSteppedOn()) {
                        drawBombNumber(g, tiles[i][j].getNumBombs(), x, y);
                    }
                }
                else if(tiles[i][j].isSteppedOn()) {
                    g.setColor(new Color(245, 245, 245));  // Almost white
                    g.fillRect(x, y,CELL_SIZE,CELL_SIZE);
                    g.setColor(new Color(220, 220, 220));  // Light gray
                    g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                    
                    // Draw the number or mine
                    drawBombNumber(g, tiles[i][j].getNumBombs(), x, y);
                }
                else {
                    // Unstepped cell
                    g.setColor(new Color(200, 200, 200));  // Gray
                    g.fillRect(x, y,CELL_SIZE,CELL_SIZE);
                    g.setColor(new Color(150, 150, 150));  // Darker gray
                    g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                    
                    // flag for finish
                    if (i == size - 1 && j == size - 1) {
                        g.setColor(new Color(178, 34, 34));  // red
                        g.setFont(new Font("SansSerif", Font.BOLD, 12));
                        g.drawString("F", x+4, y+12);
                    } else {
                        // Draw question mark for all other unstepped tiles
                        g.setColor(new Color(100, 100, 100));
                        g.setFont(new Font("SansSerif", Font.BOLD, 11));
                        g.drawString("?", x + 4, y + 12);
                    }
                }
            }
        }
    }
    
    /**
     * Helper method to draw the bomb count number in the tile
     */
    private void drawBombNumber(Graphics g, int numBombs, int x, int y) {
        if (numBombs == -1) {
            // Draw mine icon
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            g.drawString("B", x + 4, y + 12);
        }
        else {
            // COOL COLORS
            switch (numBombs) {
                case 0: g.setColor(new Color(120, 120, 120)); break; // Gray for zero
                case 1: g.setColor(new Color(25, 118, 210)); break;  // Blue
                case 2: g.setColor(new Color(56, 142, 60)); break;   // Green
                case 3: g.setColor(new Color(211, 47, 47)); break;   // Red
                case 4: g.setColor(new Color(123, 31, 162)); break;  // Purple
                case 5: g.setColor(new Color(255, 143, 0)); break;   // Orange
                case 6: g.setColor(new Color(0, 150, 136)); break;   // Teal
                case 7: g.setColor(new Color(0, 0, 0)); break;       // Black
                case 8: g.setColor(new Color(117, 117, 117)); break; // Gray
                default: g.setColor(Color.BLACK);
            }

            g.setFont(new Font("SansSerif", Font.BOLD, 13));
            
            g.drawString(Integer.toString(numBombs), x + CELL_SIZE/2 - 3, y + CELL_SIZE/2 + 5);
        }
    }
    
    @Override
    public void update() {
        repaint();
    }
}