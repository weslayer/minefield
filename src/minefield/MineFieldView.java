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

    private void initView() {
        if (model == null) return;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model == null) return;
        MineFieldModel mineModel = (MineFieldModel) model;
        int size = mineModel.getSize();
        Tile[][] tiles = mineModel.getTiles();

        // Draw the grid
        for (int i = 0; i <size; i++) {
            for (int j = 0; j < size; j++) {

                //tiles[x][y].colorTile();
                //this.add(tiles[x][y]);
                int x = i*CELL_SIZE;
                int y = j*CELL_SIZE;

                if(tiles[i][j].isGoal()) {
                    if(tiles[i][j].isSteppedOn()){
                        g.setColor(Color.GREEN);
                        g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                        g.setColor(Color.LIGHT_GRAY);
                        g.drawRect(x+1, y +1,CELL_SIZE-1,CELL_SIZE-1);
                    }
                    else{
                        g.setColor(Color.GREEN);
                        g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                        g.setColor(Color.LIGHT_GRAY);
                        g.drawRect(x+1, y +1,CELL_SIZE-1,CELL_SIZE-1);
                    }
                }
                else if(tiles[i][j].isPlayerOn()) {
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x+1, y +1,CELL_SIZE-1,CELL_SIZE-1);
                }
                else if(tiles[i][j].isSteppedOn()) {
                    g.setColor(Color.WHITE);
                    g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x+1, y +1,CELL_SIZE-1,CELL_SIZE-1);
                }
                else {
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y,CELL_SIZE,CELL_SIZE);
                    g.setColor(Color.GRAY);
                    g.drawRect(x+1, y +1,CELL_SIZE-1,CELL_SIZE-1);
                }
                g.drawString(tiles[i][j].toString(),x+2, y+15);

            }
        }
    }
}