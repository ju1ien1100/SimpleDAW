package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.sound.MidiPlayer;


// A class which represents a song, a 2d array of sounds
public class Song implements Writable {
    private static int DEFAULT_NUMTRACKS = 5;
    private static int DEFAULT_NUMMEASURE = 8;

    private Measure[][] measures;
    private int bpm;
    private boolean playing = false;
    private String name;

    //EFFECTS: constructs a new song object with a default 5*8 array of Measure
    //NOTE: bpm will have to be used for GUI to create ticks of the element to display and contribute to a more
    // sophisticated Midi in the future
    public Song(String name) {
        this.measures = new Measure[DEFAULT_NUMTRACKS][DEFAULT_NUMMEASURE];
        this.bpm = 120;
        this.name = name;

    }

    //EFFECTS: getter
    public String getName() {
        return name;
    }

    //REQUIRES: DEFAULT_NUMTRACKS > trackIndex >= 0, DEFAULT_NUMMEASURE > measureIndex >= 0
    //MODIFIES: this, measure
    //EFFECTS: adds a measure to a given row in measure and then sets the Channel of the measure
    public void addMeasure(int trackIndex, int measureIndex, Measure measure) {
        measures[trackIndex][measureIndex] = measure;
        measure.setChannel(trackIndex);
    }

    //EFFECTS: getter
    public Measure[][] getMeasures() {
        return measures;
    }

    //Effects: Plays the song by sending each measure to the midi device
    //Modifies: this
    public void pauseSong() {
        this.playing = false;
    }

    //EFFECTS: getter
    public boolean getPlaying() {
        return playing;
    }


    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public void removeMeasure(int trackIndex, int measureIndex) {
        measures[trackIndex][measureIndex] = null;
    }

    //EFFECTS: creates a new 2d array with one more row and then copies the current 2d array values in
    //Modifies: this
    public void addTrack() {
        Measure[][] newMeasure = new Measure[measures.length + 1][measures[0].length];
        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[0].length; j++) {
                newMeasure[i][j] = measures[i][j];
            }
        }
        measures = newMeasure;
    }

    //EFFECTS: creates a new 2d array with one more column and then coppies current 2d array values in
    //MODIFIES: this
    public void addMeasureColumn() {
        Measure[][] newMeasure = new Measure[measures.length][measures[0].length + 1];
        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[0].length; j++) {
                newMeasure[i][j] = measures[i][j];
            }
        }
        measures = newMeasure;
    }


//PLEASE NOTE: A TA told me to move playSong() and related functions to main, however,
    //         I didn't think this would properly reflect the Song class so I decided
    //         to keep it here.


    //EFFECTS: Sends the measure data to the midi player (access external hardware)
    public void startMeasure(List<Thread> threads, Measure measure) {
        MidiPlayer midi = new MidiPlayer();
        if (measure.getIsChord()) {
            //EFFECT: Creates a new thread object to run midi devices for different instruments in parallel
            //Lambda function
            Thread thread = new Thread(() -> {
                midi.playChord(measure.getChannel(), measure.getInstrument(), measure.getNotes());
            });
            threads.add(thread);
            thread.start();
        } else {
            Thread thread = new Thread(() -> {
                midi.playNotes(measure.getChannel(), measure.getInstrument(), measure.getNotes());
            });
            threads.add(thread);
            thread.start();
        }
    }

    //EFFECTS: dissects the 2d measures array to play one column at a time
    //Modifies: this
    public void playSong() {
        this.playing = true;
        int maxNumMeasures = measures[0].length;
        while (playing) {
            for (int i = 0; i < maxNumMeasures && playing; i++) {
                List<Thread> threads = new ArrayList<>();
                for (int j = 0; j < measures.length; j++) {
                    Measure measure = measures[j][i];
                    if (measure != null) {
                        startMeasure(threads, measure);
                    }
                }
                synchronizeThreads(threads);
            }
        }
    }

    //Effects: waits for threads to finish compiling and then plays at the same time
    // Note: I got the idea to use threads from:
    // https://stackoverflow.com/questions/18162863/how-to-run-different-methods-parallely
    // I recognized the Thread from the midiPlayer provided in class and decided
    // that it would the best way to approach synchronous playback of different midi channels
    // because the author said it was what should be used before more complex methods.
    private void synchronizeThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //EFFECTS: populates the measure map
    public HashMap<String, List<Measure>> populateMeasures() {
        HashMap<String, List<Measure>> measureMap = new HashMap<String, List<Measure>>();
        for (int i = 0; i < measures.length; i++) {
            for (int j = 0; j < measures[0].length; j++) {
                Measure measure = measures[i][j];
                if (measure != null) {
                    String instrument = measure.getInstrument();
                    if (measureMap.containsKey(measure.getInstrument())) {
                        List<Measure> instrumentMeasures = measureMap.get(instrument);
                        instrumentMeasures.add(measure);
                        measureMap.put(instrument, instrumentMeasures);
                    } else {
                        List<Measure> newMeasures = new ArrayList<>();
                        newMeasures.add(measure);
                        measureMap.put(instrument, newMeasures);
                    }
                }
            }
        }

        return measureMap;
    }


    //Effects: converts the song to a JSON file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("bpm", bpm);
        json.put("playing", playing);

        JSONArray tracksArray = new JSONArray();
        for (Measure[] track : measures) {
            JSONArray measureArray = new JSONArray();
            for (Measure measure : track) {
                if (measure != null) {
                    measureArray.put(measure.toJson());
                } else {
                    measureArray.put(JSONObject.NULL);
                }
            }
            tracksArray.put(measureArray);
        }
        json.put("tracks", tracksArray);

        return json;
    }
}
