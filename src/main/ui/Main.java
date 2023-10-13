package ui;

import model.*;
import model.Measure;
import ui.sound.MidiPlayer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static Map<String, Song> songList = new HashMap<String, Song>() {};
    private static Map<String, Measure> measureMap = new HashMap<String, Measure>() {};

    public static void main(String[] args) {
        greet();
    }



    private static void menu(Map<String, Song> songList) {
        System.out.println("MENU:");
        for (String key: songList.keySet()) {
            System.out.println(key);
        }
        Song chosenSong = songSelection();
        mainView(chosenSong);

    }

    public static void makeNewMeasure(Song userSong) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to call this measure:");
        String measureName = scanner.nextLine();
        showInstruments();
        String instrument = scanner.nextLine().toLowerCase();
        Measure userMeasure = new Measure(instrument);
        measureMap.put(measureName, userMeasure);
        populateMeasure(userMeasure);

        System.out.println("What time do you want this added (i.e what x index?)");
        int xindex  = scanner.nextInt();

        System.out.println("What channel do you want to added (i.e what y index?)");
        int yindex = scanner.nextInt();

        userSong.addMeasure(yindex, xindex, userMeasure);
        mainView(userSong);
    }

    public static void editMeasure() {
        System.out.println("Do you want to edit a measure? (yes/no)");
        System.out.println("...Remember this will change it everywhere!");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().toLowerCase();
        if (response.equals("yes")) {
            Measure editMeasure = measureSelect(measureMap);
            System.out.println("What note index do you want to change?");
            int indexResponse = scanner.nextInt();
            System.out.println("What is the new note?");
            Note newNote = populateNote();
            editMeasure.replaceNote(indexResponse, newNote);
        }
    }

    private static Measure measureSelect(Map<String, Measure> measures) {
        System.out.println("Measures available:");
        for (String key: measures.keySet()) {
            System.out.print(key + " ");
            viewMeasure(measureMap.get(key));
        }
        Measure chosenMeasure = measureSelection();
        return chosenMeasure;
    }

    public static void initNewSong() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Amazing! Lets make a new song!");
        System.out.println("What is this song named?");
        String songName = scanner.nextLine();
        Song userSong = new Song(songName);
        songList.put(songName, userSong);
        menu(songList);
    }

    public static void greet() {
        displayAsciiArt();

        System.out.println("Ready to make some music: (yes/no)");

        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();

        if (userResponse.equals("yes")) {
            initNewSong();
        } else {
            System.out.println("Okay...we'll be waiting");
            greet();
        }
    }

    public static void displayer() {

        System.out.println("Want to make a new song?: (yes/no)");

        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();

        if (userResponse.equals("yes")) {
            initNewSong();
        } else {
            System.out.println("Okay...we'll be waiting");
            menu(songList);
        }
    }




    public static void startSong(Song userSong) {
        Thread playThread = new Thread(() -> userSong.playSong());
        playThread.start();
        System.out.println("enter 'pause' to pause the song");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().toLowerCase();
        if (response.equals("pause")) {
            userSong.pauseSong();
            try {
                playThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainView(userSong);
        }
    }

    public static String checkMeasureNew() {
        System.out.println("Do you want to make a new measure: (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();
        return userResponse;
    }

    public static void doneWithSong() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you done with this song? (yes/no)");

        String response = scanner.nextLine().toLowerCase();
        if (response.equals("yes")) {
            displayer();
        }
    }

    public static void addPreMadeMeasure(Song userSong) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to place a measure you have made? (yes/no)");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("yes")) {
            Measure chosenMeasure = measureSelect(measureMap);
            System.out.println("What time do you want this added (i.e what x index?)");
            int xindex  = scanner.nextInt();

            System.out.println("What channel do you want to added (i.e what y index?)");
            int yindex = scanner.nextInt();
            userSong.addMeasure(yindex, xindex, chosenMeasure);
        }
        return;
    }

    public static void editSong(Song userSong) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to edit the song/change the array (yes/no)");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("yes")) {
            System.out.println("Do you want to add a measure column? (yes/no)");
            String measureChange = scanner.nextLine().toLowerCase();
            if (measureChange.equals("yes")) {
                userSong.addMeasureColumn();
            }
            System.out.println("Do you want to add a track row? (yes/no)");
            String addTrack = scanner.nextLine().toLowerCase();
            if (addTrack.equals("yes")) {
                userSong.addTrack();
            }
        }
    }

    public static void mainView(Song userSong) {
        viewSong(userSong);

        Scanner scanner = new Scanner(System.in);
        String userResponse = checkMeasureNew();

        if (userResponse.equals("yes")) {
            makeNewMeasure(userSong);
        } else {
            System.out.println("Do you want to play your song? (yes/no):");
            String terminal = scanner.nextLine().toLowerCase();
            if (terminal.equals("yes")) {
                startSong(userSong);
            } else {
                editSong(userSong);
                addPreMadeMeasure(userSong);
                editMeasure();
                doneWithSong();
                mainView(userSong);
            }
        }
    }

    private static void viewSong(Song userSong) {
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

    private static void viewMeasure(Measure userMeasure) {
        String[] notes = userMeasure.getStringNotes();
        System.out.print("This measure has ");
        for (String note: notes) {
            System.out.print(note + ", ");
        }
        if (userMeasure.getIsChord()) {
            System.out.print("and is a chord");
        } else {
            System.out.print("and is a melody");
        }
        System.out.println();
    }

    private static void melodyOrChord(Measure userMeasure) {
        System.out.println("Is this a chord:(yes/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();

        if (userResponse.equals("yes")) {
            userMeasure.setIsChord();
        }
    }

    private static Note populateNote() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What note (i.e C, C#, D, D#,...");
        String pitch = scanner.nextLine();

        System.out.println("Whats the duration of the note (in milliseconds)");
        long duration  = scanner.nextLong();

        System.out.println("What veloctiy do you want the note? (600 recommended)");
        int velocity = scanner.nextInt();
        Note newNote = new Note(pitch, duration, velocity);

        return newNote;
    }

    private static void populateMeasure(Measure userMeasure) {
        viewMeasure(userMeasure);

        System.out.println("Do you want to add a Note: (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();
        if (userResponse.equals("yes")) {
            Note newNote = populateNote();
            userMeasure.addNote(newNote);
            populateMeasure(userMeasure);
        } else {
            System.out.println("Are you done? (yes/no)");
            String terminal = scanner.nextLine().toLowerCase();
            if (terminal.equals("yes")) {
                melodyOrChord(userMeasure);
            } else {
                populateMeasure(userMeasure);
            }
        }
    }

    private static Song songSelection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What song do you want to work on (type exact name)");
        String userResponse = scanner.nextLine();
        if (songList.containsKey(userResponse)) {
            return songList.get(userResponse);
        } else {
            System.out.println("Hmmm we can't find what your looking for, checsk again in the menu");
            menu(songList);
            return null;
        }
    }

    private static Measure measureSelection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What measure do you want to work with (type exact name)");
        String userResponse = scanner.nextLine();
        if (measureMap.containsKey(userResponse)) {
            return measureMap.get(userResponse);
        } else {
            System.out.println("Hmmm we can't find what your looking for, check again in the menu");
            measureSelect(measureMap);
            return null;
        }
    }


    //EFFECTS: Makes my logo for FLJuJu in the command
    private static void displayAsciiArt() {
        System.out.println("  ______ _          _            _       ");
        timeOut(400);
        System.out.println(" |  ____| |        | |          | |      ");
        timeOut(300);
        System.out.println(" | |__  | |        | |_   _     | |_   _ ");
        timeOut(300);
        System.out.println(" |  __| | |    _   | | | | |_   | | | | |");
        timeOut(300);
        System.out.println(" | |    | |___| |__| | |_| | |__| | |_| |");
        timeOut(300);
        System.out.println(" |_|    |______\\____/ \\__,_|\\____/ \\__,_|");
        System.out.println();
        System.out.println();
    }

    private static void timeOut(int time) {
        try {
            Thread.sleep(time);  // Sleep for 500 milliseconds (0.5 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void showInstruments() {
        System.out.println("What instrument do you want:");
        System.out.println("-piano");
        System.out.println("-violin");
        System.out.println("-organ");
        System.out.println("-synth");
    }
}
