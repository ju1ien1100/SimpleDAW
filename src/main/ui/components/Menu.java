package ui.components;

import ui.components.MainView;
import model.Measure;
import model.Song;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//A class to show the opening menu of the application
public class Menu extends Screen {

    private static final String JSON_STORE = "./data/songList.json";
    private static Map<String, Song> songList = new HashMap<String, Song>() {};

    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;


    public Menu() {
        super();
        mainFrame.setLayout(new BorderLayout());
        initializeJsonComponents();
        loadSongList();
        initializeUIComponents();
        setupMenuPanel();
        configureMainFrame();


    }

    // EFFECT: Initializes JSON components for reading and writing song data
    private void initializeJsonComponents() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void loadSongList() {
        try {
            songList = jsonReader.read();
            System.out.println("Loaded song list from " + JSON_STORE);
        } catch (IOException | JSONException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECT: Initializes basic UI components of the Menu
    private void initializeUIComponents() {
        ImageIcon imageIcon = new ImageIcon("./src/main/ui/images/Logo.png");
        JLabel imageLabel = new JLabel(imageIcon);
        mainFrame.add(imageLabel, BorderLayout.NORTH);
    }

    // EFFECT: Sets up the menu panel with radio buttons for user interaction
    private void setupMenuPanel() {
        JPanel menuPanel = createMenuPanel();

        JRadioButton newSongButton = createRadioButton("New Song", e -> handleNewSongAction());
        JRadioButton loadSongButton = createRadioButton("Load Song", e -> handleLoadSongAction());
        JRadioButton exitButton = createRadioButton("Exit", e -> mainFrame.dispose());

        ButtonGroup menuGroup = new ButtonGroup();
        menuGroup.add(newSongButton);
        menuGroup.add(loadSongButton);
        menuGroup.add(exitButton);

        addButtonsToPanel(menuPanel, newSongButton, loadSongButton, exitButton);
        mainFrame.add(menuPanel, BorderLayout.CENTER);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
        menuPanel.add(Box.createVerticalGlue());
        return menuPanel;
    }

    private JRadioButton createRadioButton(String text, ActionListener actionListener) {
        JRadioButton button = new JRadioButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void addButtonsToPanel(JPanel panel, JRadioButton... buttons) {
        for (JRadioButton button : buttons) {
            panel.add(button);
        }
        panel.add(Box.createVerticalGlue());
    }

    private void handleNewSongAction() {
        String songName = JOptionPane.showInputDialog(mainFrame, "Enter the name of the new song:");

        // Check if a name was entered
        if (songName != null && !songName.trim().isEmpty()) {
            // Create a new Song object with the entered name
            // Assuming the Song constructor takes a name as an argument
            Song newSong = new Song(songName);

            // Add the new song to the song list and update JSON (if required)
            songList.put(songName, newSong);
            try {
                jsonWriter.open();
                jsonWriter.write(songList);
                jsonWriter.close();
                System.out.println("Saved new song to " + JSON_STORE);
            } catch (IOException ex) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }

            // Dispose the current frame and open MainView with the new song
            mainFrame.dispose();
            MainView main = new MainView(newSong, songList);
        }
    }

    private void handleLoadSongAction() {
        JFrame songSelectFrame = new JFrame("Select a Song");
        songSelectFrame.setLayout(new FlowLayout());
        songSelectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        for (String songKey : songList.keySet()) {
            JButton songButton = new JButton(songKey);
            songButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    songSelectFrame.dispose();
                    mainFrame.dispose();
                    MainView main = new MainView(songList.get(songKey), songList);
                }
            });
            songSelectFrame.add(songButton);
        }

        songSelectFrame.pack();
        songSelectFrame.setVisible(true);
    }


    // EFFECT: Configures the main application frame
    private void configureMainFrame() {
        mainFrame.pack();
        mainFrame.setSize(900, 700);
        mainFrame.setVisible(true);
    }




}
