package minefield;

import mvc.*;
import java.awt.*;
import java.util.List;

public class MineFieldView extends View {

    private static final int CELL_SIZE = 15;

    public MineFieldView(MineFieldModel model) {
        super(model);
        this.setLayout(new GridLayout(20,20));
        setPreferredSize(new Dimension(model.getSize(), model.getSize()));
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
        for (int x = 0; x < size; x+=CELL_SIZE) {
            for (int y = 0; y < size; y+=CELL_SIZE) {
                tiles[x][y].colorTile();
                this.add(tiles[x][y]);
            }
        }
    }
}