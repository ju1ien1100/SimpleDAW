package ui.components;

import model.Measure;
import model.Song;
import ui.common.SoundBox;
import ui.common.GridPanel;
import persistence.JsonReader;
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





    public MainView(Song song, Map<String, Song> songList) {
        super();

        this.song = song;
        this.measureMap = song.populateMeasures();
        this.colorMap = new HashMap<String, Color>();
        layeredPane = new JLayeredPane();
        this.songList = songList;
        //Measures Display
        jsonWriter = new JsonWriter(JSON_STORE);


        //Header Stuff
        JPanel header = header();




        mainFrame.setSize(800, 600);

        int headerHeight = (int)(mainFrame.getHeight() * 0.1);
        mainFrame.add(header, BorderLayout.NORTH);
        header.setPreferredSize(new Dimension(mainFrame.getWidth(), headerHeight));

        measureBasket();


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
                // Calculate the drop location index based on the grid
                int x = e.getX() + initialLocation.x;
                int y = e.getY() + initialLocation.y;
                int gridWidth = mainFrame.getWidth() / song.getMeasures()[0].length;
                int gridHeight = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;
                int dropTrackIndex = y / gridHeight;
                int dropMeasureIndex = x / gridWidth;

                // Update the song measure array with the new measure
                if (song.getMeasures()[0].length - 1 > dropMeasureIndex && dropMeasureIndex > 0) {
                    if (song.getMeasures().length - 1 > dropTrackIndex && dropTrackIndex > 0) {
                        song.addMeasure(dropTrackIndex, dropMeasureIndex, measureBox.getMeasure());
                    }
                }
                // Remove the measure from the old location
                song.getMeasures()[trackIndex][measureIndex] = null;
                viewSong(song);
                // Update the SoundBox location visually
                measureBox.getBoxPanel().setBounds(
                        x,
                        y,
                        measureBox.getBoxPanel().getWidth(),
                        measureBox.getBoxPanel().getHeight());
                layeredPane.revalidate();
                layeredPane.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Update the location of the SoundBox during the drag
                int x = e.getX() + initialLocation.x;
                int y = e.getY() + initialLocation.y;
                measureBox.getBoxPanel().setLocation(x, y);
                layeredPane.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Double-click detected, remove the measure from the song
                    song.removeMeasure(trackIndex, measureIndex);
                    // Optionally, you might want to remove the component from the container directly
                    layeredPane.remove(measureBox.getBoxPanel());
                    // Repaint the container after the component is removed
                    layeredPane.revalidate();
                    layeredPane.repaint();
                    // Refresh the view of the song if needed
                    viewSong(song);
                }
            }
        };

        measureBox.getBoxPanel().addMouseListener(ma);
        measureBox.getBoxPanel().addMouseMotionListener(ma);
    }

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
                // Calculate the drop location index based on the grid
                int x = e.getX() + initialLocation.x;
                int y = e.getY() + initialLocation.y;
                int gridWidth = mainFrame.getWidth() / song.getMeasures()[0].length;
                int gridHeight = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;
                int dropTrackIndex = y / gridHeight;
                int dropMeasureIndex = x / gridWidth;

                // Update the song measure array with the new measure
                if (song.getMeasures()[0].length - 1 > dropMeasureIndex && dropMeasureIndex > 0) {
                    if (song.getMeasures().length - 1 > dropTrackIndex && dropTrackIndex > 0) {
                        song.addMeasure(dropTrackIndex, dropMeasureIndex, measureBox.getMeasure());
                        makeSoundBoxDraggable(measureBox, dropTrackIndex, dropMeasureIndex);
                    }
                }
                viewSong(song);
                // Update the SoundBox location visually
                measureBox.getBoxPanel().setBounds(
                        x,
                        y,
                        (int) measureBox.getMeasure().getDuration() / 10,
                        measureBox.getBoxPanel().getHeight());
                layeredPane.revalidate();
                layeredPane.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Update the location of the SoundBox during the drag
                int x = e.getX() + initialLocation.x;
                int y = e.getY() + initialLocation.y;
                measureBox.getBoxPanel().setLocation(x, y);
                layeredPane.repaint();
            }


        };

        measureBox.getBoxPanel().addMouseListener(ma);
        measureBox.getBoxPanel().addMouseMotionListener(ma);
    }



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


    public void measureBasket() {
        List<String> instruments = new ArrayList<String>(measureMap.keySet());
         // Set the layout to null for absolute positioning

        int positionY = (int) (mainFrame.getHeight() * 0.5);
        int positionX = 0;
        int height = (int) (mainFrame.getHeight() * 0.5) / song.getMeasures().length;
        for (String instrument : instruments) {
            List<Measure> measures = measureMap.get(instrument);
            Color color = getRandomBrightColor();
            colorMap.put(instrument, color);

            for (int i = 0; i < measures.size(); i++) {
                Measure measure = measures.get(i);
                int duration = (int) measure.getDuration() / 10;
                MeasureBox measureBox = new MeasureBox(duration, height, color, measure);
                makeMeasureBoxDraggable(measureBox);
                JPanel boxPanel = measureBox.getBoxPanel();
                boxPanel.setBounds(positionX, positionY, duration, height);
                layeredPane.add(boxPanel, MEASURE_BOX_LAYER - 1);

                if (positionX + duration >= mainFrame.getWidth()) {
                    positionY += height;
                    positionX = 0;
                } else {
                    positionX += duration;
                }
            }

        }
        layeredPane.revalidate();
        layeredPane.repaint();

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


    //EFFECTS: Creates the JPanel for the Header
    public JPanel header() {
        //Header stuff
        JPanel header = new JPanel(new GridLayout(1, 5));

        JRadioButton saveButton = new JRadioButton("Save");
        JRadioButton exitButton = new JRadioButton("Exit");
        JRadioButton playButton = new JRadioButton("Play");
        JRadioButton pauseButton = new JRadioButton("Pause");
        JRadioButton newMeasureButton = new JRadioButton("New");

        Color backgroundColor = new Color(193, 239, 172, 190);
        saveButton.setBackground(backgroundColor);
        exitButton.setBackground(backgroundColor);
        playButton.setBackground(backgroundColor);
        pauseButton.setBackground(backgroundColor);
        newMeasureButton.setBackground(backgroundColor);


        header.add(saveButton);
        header.add(exitButton);
        header.add(playButton);
        header.add(pauseButton);
        header.add(newMeasureButton);


        //Plays the song
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSong(song);

            }
        });

        //Pauses the song
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSong(song);
            }
        });

        //Pauses the song
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new Menu();
            }
        });

        //Pauses the song
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        }
        );


        newMeasureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });



        return header;
    }

    public void addMeasureToBasket(Measure measure) {
        int positionY = (int) (mainFrame.getHeight() * 0.5) + 150;
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
