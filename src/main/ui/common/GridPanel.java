package ui.common;

import javax.swing.*;
import java.awt.*;

//A panel in the grid of the trackBasket
public class GridPanel extends JPanel {
    private int rows;
    private int columns;
    private int cellWidth;
    private int cellHeight;

    public GridPanel(int rows, int columns, int cellWidth, int cellHeight) {
        this.rows = rows;
        this.columns = columns;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        setOpaque(false);
    }


    //Properly makes grid
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int x = j * cellWidth;
                int y = i * cellHeight;
                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }
}
