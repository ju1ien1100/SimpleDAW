package model;

import java.util.ArrayList;
import java.util.List;

import sound.MidiPlayer;

public class Song {
    private static int DEFAULT_NUMTRACKS = 5;
    private static int DEFAULT_NUMMEASURE = 8;

    private Measure[][] measures;
    private int bpm;
    private boolean playing = false;

    public Song() {
        this.measures = new Measure[DEFAULT_NUMTRACKS][DEFAULT_NUMMEASURE];
        this.bpm = 120;
    }

    public void addMeasure(int trackIndex, int measureIndex, Measure measure) {
        measures[trackIndex][measureIndex] = measure;
        measure.setChannel(trackIndex);
    }

    public Measure[][] getMeasures() {
        return measures;
    }

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

    private void synchronizeThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseSong() {
        this.playing = false;
    }



}
