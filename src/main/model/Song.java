package model;

import java.util.ArrayList;
import java.util.List;

import ui.sound.MidiPlayer;

public class Song {
    private static int DEFAULT_NUMTRACKS = 5;
    private static int DEFAULT_NUMMEASURE = 8;

    private Measure[][] measures;
    private int bpm;
    private boolean playing = false;

    //EFFECTS: constructs a new song object with a 8*5 array of measures
    //NOTE: Later versions will abstract this to be an arbitrary sized array, but makes command line hard to interpret
    public Song() {
        this.measures = new Measure[DEFAULT_NUMTRACKS][DEFAULT_NUMMEASURE];
        this.bpm = 120;
    }

    //REQUIRES: DEFAULT_NUMTRACKS > trackIndex >= 0, DEFAULT_NUMMEASURE > measureIndex >= 0
    //MODIFIES: this, measure
    //EFFECTS: adds a measure to a given row in measure and then sets the Channel of the measure
    public void addMeasure(int trackIndex, int measureIndex, Measure measure) {
        measures[trackIndex][measureIndex] = measure;
        measure.setChannel(trackIndex);
    }

    public Measure[][] getMeasures() {
        return measures;
    }

    //Effects: Plays the song by sending each measure to the midi device
    //Modifies: this
    public void pauseSong() {
        this.playing = false;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void addTrack() {
        Measure[][] newMeasure = new Measure[measures.length + 1][measures[0].length];
        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[0].length; j++) {
                newMeasure[i][j] = measures[i][j];
            }
        }
        measures = newMeasure;
    }

    public void addMeasureColumn() {
        Measure[][] newMeasure = new Measure[measures.length][measures[0].length + 1];
        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[0].length; j++) {
                newMeasure[i][j] = measures[i][j];
            }
        }
        measures = newMeasure;
    }


    //Sends the measure data to the midi player (access external hardware)
    public void playSong() {
        MidiPlayer midi = new MidiPlayer();
        this.playing = true;
        int maxNumMeasures = measures[0].length;
        while (playing) {
            for (int i = 0; i < maxNumMeasures && playing; i++) {
                List<Thread> threads = new ArrayList<>();
                for (int j = 0; j < measures.length; j++) {
                    Measure measure = measures[j][i];
                    if (measure != null) {
                        Thread thread = new Thread(() -> {
                            midi.playNotes(measure.getChannel(), measure.getInstrument(), measure.getNotes());
                        });
                        threads.add(thread);
                        thread.start();
                    }
                }
                synchronizeThreads(threads);
            }
        }
    }

    //Effects: waits for threads to finish compiling and then plays at the same time
    private void synchronizeThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
