package ui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class SoundBox {

    private long duration;
    private Color color;
    private long height;
    private JPanel boxPanel;
    private int positionX;
    private int positionY;

    private Point initialClick;



    public SoundBox(long duration, long height, Color color) {
        this.duration = duration;
        this.height = height;
        this.color = color;

        boxPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(color);

                g2d.fillRoundRect(0,0, (int) duration, (int) height, 10, 10);

            }
        };


        boxPanel.setPreferredSize(new Dimension((int) duration, (int) height));
    }


    public JPanel getBoxPanel() {
        return boxPanel;
    }


}



