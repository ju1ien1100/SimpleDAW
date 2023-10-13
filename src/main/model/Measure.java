package model;

import java.util.ArrayList;
import java.util.List;

public class Measure {
    private List<Note> notes;
    private String instrument;
    private int channel;
    private boolean ischord;

    //Requires: A valid instrument name
    //Effects: constructs a new measure with an empty list of notes, the instrument and leaves channel to be set
    public Measure(String instrument) {
        this.notes = new ArrayList<>();
        this.instrument = instrument;
        this.channel = -69;
        this.ischord = false;
    }

    //EFFECTS: adds note object to a measure
    //MODIFIES: this
    public void addNote(Note note) {
        this.notes.add(note);
    }

    // Requires: index > 0
    //Modifies: this, notes
    //EFFECTS: replaces the note at the inputed index
    public boolean replaceNote(int index, Note note) {
        if (this.notes.size() - 1 < index) {
            return false;
        } else {
            this.notes.add(index, note);
            return true;
        }
    }

    //EFFECTS:
    public void setIsChord() {
        ischord = true;
    }

    public void setIsNotChord() {
        ischord = false;
    }

    public boolean getIsChord() {
        return ischord;
    }


    public int getChannel() {
        return channel;
    }

    //MODIFIES: this
    //EFFECTS: sets the channel of the measure
    public void setChannel(int channel) {
        this.channel = channel;
    }

    //EFFECTS: getter
    public List<Note> getNotes() {
        return notes;
    }

    //EFFECTS:Gets the string representations of the note pitches
    public String[] getStringNotes() {
        int numNotes = this.notes.size();
        String[] notes = new String[numNotes];

        for (int i = 0; i < numNotes; i++) {
            notes[i] = this.notes.get(i).getPitch();
        }
        return notes;
    }

    //EFFECTS: getter
    public String getInstrument() {
        return instrument;
    }

    //EFFECTS: setter
    //MODIFIES: this
    public void setInstrument(String instrumentNew) {
        instrument = instrumentNew;
    }

}
