package ui;

import model.*;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        greet();
    }

    public static void greet() {
        displayAsciiArt();

        System.out.println("Do you want to make a new song?:");

        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();

        if (userResponse.equals("yes")) {
            System.out.println("Amazing! Lets get you started");
            Song userSong = new Song();
            mainView(userSong);
        } else {
            System.out.println("Okay...we'll be waiting");
            greet();
        }
    }

    public static void showInstruments() {
        System.out.println("What instrument do you want:");
        System.out.println("-piano");
        System.out.println("-violin");
        System.out.println("-organ");
        System.out.println("-synth");
    }

    public static void mainView(Song userSong) {
        viewSong(userSong);

        System.out.println("Do you want to add a measure:");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();

        if (userResponse.equals("yes")) {
            showInstruments();
            String instrument = scanner.nextLine().toLowerCase();
            Measure userMeasure = new Measure(instrument);
            populateMeasure(userMeasure);

            System.out.println("What time do you want this added (i.e what x index?)");
            int xindex  = scanner.nextInt();

            System.out.println("What channel do you want to added (i.e what y index?)");
            int yindex = scanner.nextInt();

            userSong.addMeasure(yindex, xindex, userMeasure);
            mainView(userSong);
        } else {
            System.out.println("Do you want to play your song?:");
            String terminal = scanner.nextLine().toLowerCase();
            if (terminal.equals("yes")) {
                Thread playThread = new Thread(() -> userSong.playSong());
                playThread.start();
                System.out.println("enter 'pause' to pause the song");

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
            } else {
                mainView(userSong);
            }
        }
    }

    private static void viewSong(Song userSong) {
        Measure[][] measures = userSong.getMeasures();

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
        System.out.println();
    }

    private static void populateMeasure(Measure userMeasure) {
        viewMeasure(userMeasure);
        System.out.println("Do you want to add a Note:");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().toLowerCase();
        if (userResponse.equals("yes")) {

            System.out.println("What note (i.e C, C#, D, D#,...");
            String pitch = scanner.nextLine();

            System.out.println("Whats the duration of the note (in milliseconds)");
            long duration  = scanner.nextLong();

            System.out.println("What veloctiy do you want the note? (600 recommended)");
            int velocity = scanner.nextInt();
            Note newNote = new Note(pitch, duration, velocity);
            userMeasure.addNote(newNote);
            populateMeasure(userMeasure);

        } else {
            System.out.println("Are you done?");
            String terminal = scanner.nextLine().toLowerCase();
            if (terminal.equals("yes")) {
                return;
            } else {
                populateMeasure(userMeasure);
            }
        }



    }

    private static void timeOut(int time) {
        try {
            Thread.sleep(time);  // Sleep for 500 milliseconds (0.5 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void displayAsciiArt() {
        System.out.println("  ______ _          _            _       ");
        timeOut(300);
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



}
