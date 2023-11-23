package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


//Ab abstracted screen class with the necessary methods to
// display the screen properly
public abstract class Screen {

    protected JFrame mainFrame;

    public Screen() {
        initializeScreen();
    }

    private void initializeScreen() {
        mainFrame = new JFrame("FLJuJu");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }



}
