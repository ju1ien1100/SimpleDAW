package ui;

import ui.components.Menu;
import ui.components.MainView;
import model.*;
import model.Measure;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.sound.MidiPlayer;
import javax.swing.*;


import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {
//    private static final String JSON_STORE = "./data/songList.json";
//    private static Map<String, Song> songList = new HashMap<String, Song>() {};
//    private static HashMap<String, Measure> measureMap = new HashMap<String, Measure>() {};
//    private static JsonWriter jsonWriter;
//    private static JsonReader jsonReader;

    public static void main(String[] args) {
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create and show your main view here
                new Menu();
            }
        });
    }

//    private static void displayOptions() {
//        System.out.println("MENU:");
//        System.out.println("1 - Pick a song to work on");
//        System.out.println("2 - Save current work to file");
//        System.out.println("3 - Load previous work from file");
//        System.out.println("4 - Exit");
//    }
//
//    //EFFECTS: menu to choose between writing to JSON or working on a song
//    private static void menu(Map<String, Song> songList) {
//        displayOptions();
//        Scanner scanner = new Scanner(System.in);
//        String option = scanner.nextLine();
//        switch (option) {
//            case "1":
//                chooseSongToWorkOn(songList);
//                break;
//            case "2":
//                saveSongList();
//                break;
//            case "3":
//                loadSongList();
//                break;
//            case "4":
//                System.out.println("Goodbye!");
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Invalid option, please enter a number between 1 and 4");
//                menu(songList);
//        }
//    }
//
//    //EFFECTS: shows the menu of songs available to pick from
//    private static void chooseSongToWorkOn(Map<String, Song> songList) {
//        System.out.println("Available Songs:");
//        for (String key : songList.keySet()) {
//            System.out.println(key);
//        }
//        Song chosenSong = songSelection();
//        if (chosenSong != null) {
//            mainView(chosenSong);
//        } else {
//            menu(songList);
//        }
//    }
//
//    //EFFECTS: makes a new measure and adds it to the hashmap of measures, goes to populate measure
//    public static void makeNewMeasure(Song userSong) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("What do you want to call this measure:");
//        String measureName = scanner.nextLine();
//        showInstruments();
//        String instrument = scanner.nextLine().toLowerCase();
//        Measure userMeasure = new Measure(instrument);
//        measureMap.put(measureName, userMeasure);
//        populateMeasure(userMeasure);
//
//        System.out.println("What time do you want this added (i.e what x index?)");
//        int xindex  = scanner.nextInt();
//
//        System.out.println("What channel do you want to added (i.e what y index?)");
//        int yindex = scanner.nextInt();
//
//        userSong.addMeasure(yindex, xindex, userMeasure);
//        mainView(userSong);
//    }
//
//    //EFFECTS: Allow users to edit a premade measure by changing a certain note
//    public static void editMeasure() {
//        System.out.println("Do you want to edit a measure? (yes/no)");
//        System.out.println("...Remember this will change it everywhere!");
//        Scanner scanner = new Scanner(System.in);
//        String response = scanner.nextLine().toLowerCase();
//        if (response.equals("yes")) {
//            Measure editMeasure = measureSelect(measureMap);
//            System.out.println("What note index do you want to change?");
//            int indexResponse = scanner.nextInt();
//            System.out.println("What is the new note?");
//            Note newNote = populateNote();
//            editMeasure.replaceNote(indexResponse, newNote);
//        }
//    }
//
//    //EFFECTS: Menu which displays all the measures available
//    private static Measure measureSelect(Map<String, Measure> measures) {
//        System.out.println("Measures available:");
//        for (String key: measures.keySet()) {
//            System.out.print(key + " ");
//            viewMeasure(measureMap.get(key));
//        }
//        Measure chosenMeasure = measureSelection();
//        return chosenMeasure;
//    }
//
//    //EFFECTS: adds a new song to the menu of songs and adds the song to the hashmap of songs
//    public static void initNewSong() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Amazing! Lets make a new song!");
//        System.out.println("What is this song named?");
//        String songName = scanner.nextLine();
//        Song userSong = new Song(songName);
//        songList.put(songName, userSong);
//        menu(songList);
//    }
//
//    //EFFECTS: first entry point of the application which displays logo and waits for user to want to make a song
//    public static void greet() {
//        AsciiArt.displayAsciiArt();
//
//        System.out.println("Ready to make some music or load previous work? (make/load/exit):");
//
//        Scanner scanner = new Scanner(System.in);
//        String userResponse = scanner.nextLine().toLowerCase();
//
//        switch (userResponse) {
//            case "make":
//                initNewSong();
//                break;
//            case "load":
//                loadSongList();
//                break;
//            case "exit":
//                System.out.println("Goodbye!");
//                System.exit(0);
//                break;
//            default:
//                System.out.println("Invalid option, please enter 'make', 'load', or 'exit'");
//                greet();
//        }
//    }
//
//
//
//    //EFFECTS: Checks if the user wants to make a new song or not, if not returns to song selection menu
//    public static void displayer() {
//
//        System.out.println("Want to make a new song?: (yes/no)");
//
//        Scanner scanner = new Scanner(System.in);
//        String userResponse = scanner.nextLine().toLowerCase();
//
//        if (userResponse.equals("yes")) {
//            initNewSong();
//        } else {
//            System.out.println("Okay...we'll be waiting");
//            menu(songList);
//        }
//    }
//
//    //EFFECTS: saves the song list to file
//    private static void saveSongList() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(songList);
//            jsonWriter.close();
//            System.out.println("Saved song list to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//        menu(songList);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads song list from file
//    private static void loadSongList() {
//        try {
//            songList = jsonReader.read();
//            System.out.println("Loaded song list from " + JSON_STORE);
//        } catch (IOException | JSONException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//        menu(songList);
//    }
//
//
//
//    //EFFECTS: plays the song and waits for user to enter pause before returning to main view
//    public static void startSong(Song userSong) {
//        Thread playThread = new Thread(() -> userSong.playSong());
//        playThread.start();
//        System.out.println("enter 'pause' to pause the song");
//        Scanner scanner = new Scanner(System.in);
//        String response = scanner.nextLine().toLowerCase();
//        if (response.equals("pause")) {
//            userSong.pauseSong();
//            try {
//                playThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mainView(userSong);
//        }
//    }
//
//    //EFFECTS: asks if the user wants to make a new measure
//    public static String checkMeasureNew() {
//        System.out.println("Do you want to make a new measure: (yes/no)");
//        Scanner scanner = new Scanner(System.in);
//        String userResponse = scanner.nextLine().toLowerCase();
//        return userResponse;
//    }
//
//    //EFFECTS: checks if user is done editing song and returns to menu of song selection
//    public static void doneWithSong() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Are you done with this song? (yes/no)");
//
//        String response = scanner.nextLine().toLowerCase();
//        if (response.equals("yes")) {
//            displayer();
//        }
//    }
//
//    //EFFECTS: enables user to add a premade measure into the song
//    public static void addPreMadeMeasure(Song userSong) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Do you want to place a measure you have made? (yes/no)");
//        String response = scanner.nextLine().toLowerCase();
//
//        if (response.equals("yes")) {
//            Measure chosenMeasure = measureSelect(measureMap);
//            System.out.println("What time do you want this added (i.e what x index?)");
//            int xindex  = scanner.nextInt();
//
//            System.out.println("What channel do you want to added (i.e what y index?)");
//            int yindex = scanner.nextInt();
//            userSong.addMeasure(yindex, xindex, chosenMeasure);
//        }
//        return;
//    }
//
//    //EFFECTS: changes the size of the array depending on the user inputs
//    public static void editSong(Song userSong) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Do you want to edit the song/change the array (yes/no)");
//        String response = scanner.nextLine().toLowerCase();
//
//        if (response.equals("yes")) {
//            System.out.println("Do you want to add a measure column? (yes/no)");
//            String measureChange = scanner.nextLine().toLowerCase();
//            if (measureChange.equals("yes")) {
//                userSong.addMeasureColumn();
//            }
//            System.out.println("Do you want to add a track row? (yes/no)");
//            String addTrack = scanner.nextLine().toLowerCase();
//            if (addTrack.equals("yes")) {
//                userSong.addTrack();
//            }
//        }
//    }
//
//    //EFFECTS: main view to work on a song. Checks if you want to play then works on editing features of song
//    public static void mainView(Song userSong) {
//        viewSong(userSong);
//
//        Scanner scanner = new Scanner(System.in);
//        String userResponse = checkMeasureNew();
//
//        if (userResponse.equals("yes")) {
//            makeNewMeasure(userSong);
//        } else {
//            System.out.println("Do you want to play your song? (yes/no):");
//            String terminal = scanner.nextLine().toLowerCase();
//            if (terminal.equals("yes")) {
//                startSong(userSong);
//            } else {
//                editSong(userSong);
//                addPreMadeMeasure(userSong);
//                editMeasure();
//                doneWithSong();
//                mainView(userSong);
//            }
//        }
//    }
//
//    //EFFECTS: Prints out the array which represents the song with the measures in the corresponding place
//    private static void viewSong(Song userSong) {
//        Measure[][] measures = userSong.getMeasures();
//        System.out.println("Song:" + userSong.getName());
//        for (int i = 0; i < measures.length; i++) {
//            for (int j = 0; j < measures[i].length; j++) {
//                if (measures[i][j] != null) {
//                    System.out.print(" " + measures[i][j].getInstrument() + " ");
//                } else {
//                    System.out.print(" empty ");
//                }
//            }
//            System.out.println();
//        }
//    }
//
//    //EFFECTS: prints out all the notes in the measure and if it is a melody or chord
//    private static void viewMeasure(Measure userMeasure) {
//        String[] notes = userMeasure.getStringNotes();
//        System.out.print("This measure has ");
//        for (String note: notes) {
//            System.out.print(note + ", ");
//        }
//        if (userMeasure.getIsChord()) {
//            System.out.print("and is a chord");
//        } else {
//            System.out.print("and is a melody");
//        }
//        System.out.println();
//    }
//
//    //EFFECTS: Determines if the measure should be a melody or a chord
//    private static void melodyOrChord(Measure userMeasure) {
//        System.out.println("Is this a chord:(yes/no)");
//        Scanner scanner = new Scanner(System.in);
//        String userResponse = scanner.nextLine().toLowerCase();
//
//        if (userResponse.equals("yes")) {
//            userMeasure.setIsChord();
//        }
//    }
//
//    //EFFECTS: method to make a new note object
//    private static Note populateNote() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("What note (i.e C, C#, D, D#,...");
//        String pitch = scanner.nextLine();
//
//        System.out.println("Whats the duration of the note (in milliseconds)");
//        long duration  = scanner.nextLong();
//
//        System.out.println("What veloctiy do you want the note? (600 recommended)");
//        int velocity = scanner.nextInt();
//        Note newNote = new Note(pitch, duration, velocity);
//
//        return newNote;
//    }
//
//    //EFFECTS: method to add notes to measure and determine if it will be a chord or melody
//
//    private static void populateMeasure(Measure userMeasure) {
//        viewMeasure(userMeasure);
//
//        System.out.println("Do you want to add a Note: (yes/no)");
//        Scanner scanner = new Scanner(System.in);
//        String userResponse = scanner.nextLine().toLowerCase();
//        if (userResponse.equals("yes")) {
//            Note newNote = populateNote();
//            userMeasure.addNote(newNote);
//            populateMeasure(userMeasure);
//        } else {
//            System.out.println("Are you done? (yes/no)");
//            String terminal = scanner.nextLine().toLowerCase();
//            if (terminal.equals("yes")) {
//                melodyOrChord(userMeasure);
//            } else {
//                populateMeasure(userMeasure);
//            }
//        }
//    }
//
//    //EFFECTS: menu to select a song from premade songs
//    private static Song songSelection() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("What song do you want to work on (type exact name)");
//        String userResponse = scanner.nextLine();
//        if (songList.containsKey(userResponse)) {
//            return songList.get(userResponse);
//        } else {
//            System.out.println("Hmmm we can't find what your looking for, checsk again in the menu");
//            menu(songList);
//            return null;
//        }
//    }
//
//    //EFFECTS: Menu to select a measure from premade measures
//    private static Measure measureSelection() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("What measure do you want to work with (type exact name)");
//        String userResponse = scanner.nextLine();
//        if (measureMap.containsKey(userResponse)) {
//            return measureMap.get(userResponse);
//        } else {
//            System.out.println("Hmmm we can't find what your looking for, check again in the menu");
//            measureSelect(measureMap);
//            return null;
//        }
//    }
//
//
//    //EFFECTS: this displays some instruments which can be used
//    public static void showInstruments() {
//        System.out.println("What instrument do you want:");
//        System.out.println("-piano");
//        System.out.println("-violin");
//        System.out.println("-organ");
//        System.out.println("-synth");
//    }
}
