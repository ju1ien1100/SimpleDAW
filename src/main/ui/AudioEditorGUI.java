package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

public class AudioEditorGUI {
    private JFrame mainFrame;
    private JPanel trackPanel;
    private JButton playButton;
    private Song currentSong;

    public AudioEditorGUI() {
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("Audio Editor");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup menu panel
        JPanel menuPanel = new JPanel(new GridLayout(0, 1));
        JRadioButton newSongButton = new JRadioButton("New Song");
        JRadioButton loadSongButton = new JRadioButton("Load Song");
        JRadioButton exitButton = new JRadioButton("Exit");
        ButtonGroup menuGroup = new ButtonGroup();
        menuGroup.add(newSongButton);
        menuGroup.add(loadSongButton);
        menuGroup.add(exitButton);
        menuPanel.add(newSongButton);
        menuPanel.add(loadSongButton);
        menuPanel.add(exitButton);

        // Setup main view
        trackPanel = new JPanel();
        trackPanel.setLayout(new BoxLayout(trackPanel, BoxLayout.Y_AXIS));

        // Add components to the frame
        mainFrame.add(menuPanel, BorderLayout.NORTH);
        mainFrame.add(trackPanel, BorderLayout.CENTER);

        // Pack and set visible
        mainFrame.pack();
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);

        // Action listeners for menu buttons
        newSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement new song creation logic
            }
        });

        loadSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement song loading logic
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
    }



}
