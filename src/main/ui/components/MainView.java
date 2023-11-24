package ui.components;

import model.Measure;
import model.Song;
import ui.common.GridPanel;
import persistence.JsonWriter;
import ui.common.MeasureCreationListener;

import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;


// Main view for editing the song
public class MainView extends Screen {

    private Song song;
    private volatile boolean isPaused = false;
    private static HashMap<String, List<Measure>> measureMap;
    private static HashMap<String, Color> colorMap;
    private static final String JSON_STORE = "./data/songList.json";
    private static Map<String, Song> songList = new HashMap<String, Song>() {};
    private static JsonWriter jsonWriter;
    private JLayeredPane layeredPane;

    private static final int GRID_LAYER = JLayeredPane.DEFAULT_LAYER;
    private static final int MEASURE_BOX_LAYER = 300;




    //COnstructor
    public MainView(Song song, Map<String, Song> songList) {
        super();
        initializeFields(song, songList);


        mainFrame.setSize(800, 600);

        setupHeader();
        setupMeasureBaskets();

        int trackBasketHeight = (int)(mainFrame.getHeight() * 0.5);
        int gridWidth = mainFrame.getWidth() / song.getMeasures()[0].length;
        int gridHeight = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;

        GridPanel gridPanel = new GridPanel(
                song.getMeasures().length,
                song.getMeasures()[0].length,
                gridWidth,
                gridHeight);
        gridPanel.setBounds(0, 0, mainFrame.getWidth(), trackBasketHeight);
        gridPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), trackBasketHeight));
        layeredPane.add(gridPanel, GRID_LAYER);

        trackBasket();
        layeredPane.setPreferredSize(new Dimension(mainFrame.getWidth(), (int)(mainFrame.getHeight() * 0.7)));
        mainFrame.add(layeredPane, BorderLayout.CENTER);


        // Pack and set visible
        mainFrame.pack();

        mainFrame.setVisible(true);


    }

    // EFFECTS: Initializes fields of MainView with given song and songList
    private void initializeFields(Song song, Map<String, Song> songList) {
        this.song = song;
        this.measureMap = song.populateMeasures();
        this.colorMap = new HashMap<>();
        this.songList = songList;
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.layeredPane = new JLayeredPane();
    }


    //EFffects: makes the soundbox moveable and placeable on grid
    public void makeSoundBoxDraggable(MeasureBox measureBox, int trackIndex, int measureIndex) {
        MouseAdapter ma = new MouseAdapter() {
            Point initialLocation;

            @Override
            public void mousePressed(MouseEvent e) {
                initialLocation = measureBox.getBoxPanel().getLocation();
                layeredPane.moveToFront(measureBox.getBoxPanel());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dropMeasureBox(e, measureBox, initialLocation, trackIndex, measureIndex);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateMeasureBoxLocationDuringDrag(measureBox, e, initialLocation);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                    removeMeasure(e, measureBox, trackIndex, measureIndex);
            }
        };
        measureBox.getBoxPanel().addMouseListener(ma);
        measureBox.getBoxPanel().addMouseMotionListener(ma);
    }

    public void dropMeasureBox(MouseEvent e, MeasureBox measureBox, Point initial, int trackIndex, int measureIndex) {
        int x = e.getX() + initial.x;
        int y = e.getY() + initial.y;

        int dropTrackIndex = Math.round((float)y / getGridHeight());
        int dropMeasureIndex = Math.round((float)x / getGridWidth());

        x = dropMeasureIndex * getGridWidth();
        y = dropTrackIndex * getGridHeight();
        song.removeMeasure(trackIndex, measureIndex);
        updateSongWithNewMeasure(dropTrackIndex, dropMeasureIndex, measureBox);
        updateMeasureBoxVisuals(measureBox, x, y);
    }



    public void removeMeasure(MouseEvent e, MeasureBox measureBox, int trackIndex, int measureIndex) {
        if (e.getClickCount() == 2) {
            song.removeMeasure(trackIndex, measureIndex);
            layeredPane.remove(measureBox.getBoxPanel());
            layeredPane.revalidate();
            layeredPane.repaint();
        }
    }

    public int getGridWidth() {
        return mainFrame.getWidth() / song.getMeasures()[0].length;
    }
    // Snapping to the nearest grid

    public int getGridHeight() {
        return  (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;
    }

    //EFfects: makes a measureBox moveable in the JLayeredPane
    public void makeMeasureBoxDraggable(MeasureBox measureBox) {
        MouseAdapter ma = new MouseAdapter() {
            Point initialLocation;

            @Override
            public void mousePressed(MouseEvent e) {
                initialLocation = measureBox.getBoxPanel().getLocation();
                layeredPane.moveToFront(measureBox.getBoxPanel());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMeasureBoxDrop(e, measureBox, initialLocation);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateMeasureBoxLocationDuringDrag(measureBox, e, initialLocation);
            }
        };
        measureBox.getBoxPanel().addMouseListener(ma);
        measureBox.getBoxPanel().addMouseMotionListener(ma);
    }

    //Effects: function for handling the release of the measureBox
    private void handleMeasureBoxDrop(MouseEvent e, MeasureBox measureBox, Point initialLocation) {
        int x = e.getX() + initialLocation.x;
        int y = e.getY() + initialLocation.y;
        int gridWidth = mainFrame.getWidth() / song.getMeasures()[0].length;
        int gridHeight = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;
        int dropTrackIndex = (int) (y + mainFrame.getHeight() * 0.1) / gridHeight;
        int dropMeasureIndex = x / gridWidth;

        if (isValidDropPosition(dropTrackIndex, dropMeasureIndex)) {
            updateSongWithNewMeasure(dropTrackIndex, dropMeasureIndex, measureBox);
        }
        updateMeasureBoxVisuals(measureBox, x, y);
        viewSong(song);
    }

    //Effects: checks if it is a valid position to drop inside of song
    private boolean isValidDropPosition(int trackIndex, int measureIndex) {
        if (trackIndex > 0 && trackIndex < song.getMeasures().length) {
            if (measureIndex > 0 && measureIndex < song.getMeasures()[0].length) {
                return true;
            }
            return false;
        }
        return false;
    }

    //Effects: updates the song with the new measure being dropped
    private void updateSongWithNewMeasure(int trackIndex, int measureIndex, MeasureBox measureBox) {
        song.addMeasure(trackIndex, measureIndex, measureBox.getMeasure());
        makeSoundBoxDraggable(measureBox, trackIndex, measureIndex);
        viewSong(song);
    }

    //EFfects: updates the visual of the layered pane
    private void updateMeasureBoxVisuals(MeasureBox measureBox, int x, int y) {
        measureBox.getBoxPanel().setBounds(
                x,
                y,
                (int) measureBox.getMeasure().getDuration() / 10,
                measureBox.getBoxPanel().getHeight());
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    //Effects: function for inside the drag moment
    private void updateMeasureBoxLocationDuringDrag(MeasureBox measureBox, MouseEvent e, Point initialLocation) {
        int x = e.getX() + initialLocation.x;
        int y = e.getY() + initialLocation.y;
        measureBox.getBoxPanel().setLocation(x, y);
        layeredPane.repaint();
    }




    //Effects: constructs the grid panel for the songs
    public void trackBasket() {
        Measure[][] measures = song.getMeasures();
        int gridWidth = mainFrame.getWidth() / song.getMeasures()[0].length;
        int gridHeight = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;

        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[0].length; j++) {
                Measure measure = measures[i][j];
                if (measure != null) {
                    Color color = colorMap.get(measure.getInstrument());
                    int duration = (int) measure.getDuration() / 10;
                    int height = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;
                    MeasureBox measureBox = new MeasureBox(duration, height, color, measure);
                    makeSoundBoxDraggable(measureBox, i, j);
                    JPanel boxPanel = measureBox.getBoxPanel();

                    // Correct the bounds setting
                    boxPanel.setBounds(j * gridWidth, i * gridHeight, duration, height);
                    boxPanel.setDoubleBuffered(true);
                    // Add to the layered pane using an appropriate layer value
                    layeredPane.add(boxPanel, MEASURE_BOX_LAYER - 1);
                }
            }
        }
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    //Effects: sets up the measure basket for placing measures
    private void setupMeasureBaskets() {
        List<String> instruments = new ArrayList<>(measureMap.keySet());
        int positionY = (int) (mainFrame.getHeight() * 0.5);
        int positionX = 0;
        int height = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;

        for (String instrument : instruments) {
            Color color = getRandomBrightColor();
            colorMap.put(instrument, color);
            positionX = processMeasures(measureMap.get(instrument), positionX, positionY, height, color);
        }
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    //Effects: makes measure box for each measure in song
    private int processMeasures(List<Measure> measures, int positionX, int positionY, int height, Color color) {
        for (Measure measure : measures) {
            int duration = (int) measure.getDuration() / 10;
            positionX = addMeasureBox(measure, duration, height, color, positionX, positionY);
        }
        return positionX;
    }

    //Effect: function for making one measure box
    private int addMeasureBox(Measure measure, int duration, int height, Color color, int positionX, int positionY) {
        MeasureBox measureBox = new MeasureBox(duration, height, color, measure);
        makeMeasureBoxDraggable(measureBox);
        JPanel boxPanel = measureBox.getBoxPanel();
        boxPanel.setBounds(positionX, positionY, duration, height);
        layeredPane.add(boxPanel, MEASURE_BOX_LAYER - 1);

        if (positionX + duration >= mainFrame.getWidth()) {
            return 0;
        } else {
            return positionX + duration;
        }
    }


    //EFFECTS gets a random bright color
    public Color getRandomBrightColor() {
        Random random = new Random();

        // Ensure that at least one of the RGB values is above 128
        int red = random.nextInt(64) + 100;   // 128 to 255
        int green = random.nextInt(111) + 144; // 128 to 255
        int blue = random.nextInt(64) + 100;  // 128 to 255

        return new Color(red, green, blue);
    }

    // EFFECTS: Sets up the header of the main view
    private void setupHeader() {
        JPanel header = header();
        int headerHeight = (int)(mainFrame.getHeight() * 0.1);
        header.setPreferredSize(new Dimension(mainFrame.getWidth(), headerHeight));
        mainFrame.add(header, BorderLayout.NORTH);
    }

    // EFFECTS: Creates the JPanel for the Header
    public JPanel header() {
        JPanel header = new JPanel(new GridLayout(1, 5));
        Color backgroundColor = new Color(193, 239, 172, 190);

        JRadioButton saveButton = createHeaderButton("Save", backgroundColor, e -> saveAndExit());
        JRadioButton exitButton = createHeaderButton("Exit", backgroundColor, e -> exitApplication());
        JRadioButton playButton = createHeaderButton("Play", backgroundColor, e -> startSong(song));
        JRadioButton pauseButton = createHeaderButton("Pause", backgroundColor, e -> stopSong(song));
        JRadioButton newMeasureButton = createHeaderButton("New", backgroundColor, e -> openMeasureView());

        addButtonsToPanel(header, saveButton, exitButton, playButton, pauseButton, newMeasureButton);
        header.setVisible(true);
        return header;
    }

    //Effects: function for adding buttons
    private void addButtonsToPanel(JPanel panel, JRadioButton... buttons) {
        for (JRadioButton button : buttons) {
            panel.add(button);
        }
        panel.add(Box.createVerticalGlue());
    }

    //effects: function for creating header button
    private JRadioButton createHeaderButton(String text, Color bg, ActionListener actionListener) {
        JRadioButton button = new JRadioButton(text);
        button.setBackground(bg);
        button.addActionListener(actionListener);
        return button;
    }

    //Effects: function for saving the song
    private void saveAndExit() {
        songList.put(song.getName(), song);
        try {
            jsonWriter.open();
            jsonWriter.write(songList);
            jsonWriter.close();
            System.out.println("Saved song list to " + JSON_STORE);
        } catch (FileNotFoundException er) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

        mainFrame.dispose();
        new Menu();
    }

    //Effects: function for exiting mainview
    private void exitApplication() {
        mainFrame.dispose();
        new Menu();
    }

    //Effects: function for making a new measure
    private void openMeasureView() {
        MeasureCreationListener listener = new MeasureCreationListener() {
            @Override
            public void onMeasureCreated(Measure measure) {
                addMeasureToBasket(measure);
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        };
        MeasureView measureView = new MeasureView(mainFrame, listener);
        measureView.setVisible(true);
    }

    //Effects: function for adding made emasure to measure basket
    public void addMeasureToBasket(Measure measure) {
        int positionY = (int) (mainFrame.getHeight() * 0.5) + 120;
        int positionX = 0;
        int height = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;
        String instrument = measure.getInstrument();
        Color color = colorMap.get(instrument);
        int duration = (int) measure.getDuration() / 10;
        MeasureBox measureBox = new MeasureBox(duration, height, color, measure);
        makeMeasureBoxDraggable(measureBox);
        JPanel boxPanel = measureBox.getBoxPanel();
        boxPanel.setBounds(positionX, positionY, duration, height);
        layeredPane.add(boxPanel, MEASURE_BOX_LAYER - 1);

        layeredPane.revalidate();
        layeredPane.repaint();
    }

    //EFFECTS: plays the song and waits for user
    public void startSong(Song userSong) {
        isPaused = false;
        Thread playThread = new Thread(() -> {
            while (!isPaused) {
                userSong.playSong();
            }
        });
        playThread.start();
    }

    //EFFECTS: pauses the song
    public void stopSong(Song userSong) {
        isPaused = true;
        userSong.pauseSong();
    }

    //Effects: prints song to terminal (for debuging)
    private void viewSong(Song userSong) {
        Measure[][] measures = userSong.getMeasures();
        System.out.println("Song:" + userSong.getName());
        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[i].length; j++) {
                if (measures[i][j] != null) {
                    System.out.print(" " + measures[i][j].getInstrument() + " ");
                } else {
                    System.out.print(" empty ");
                }
            }
            System.out.println();
        }
    }

}
