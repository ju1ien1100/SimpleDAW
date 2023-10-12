package model;

import java.util.ArrayList;
import java.util.List;

public class Measure {
    private List<Note> notes;
    private String instrument;
    private int channel;

    //Requires: A valid instrument name
    //Effects: constructs a new measure with an empty list of notes, the instrument and leaves channel to be set
    public Measure(String instrument) {
        this.notes = new ArrayList<>();
        this.instrument = instrument;
        this.channel = -69;
    }

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


    public int getChannel() {
        return channel;
    }

    //MODIFIES: this
    //EFFECTS: sets the channel of the measure
    public void setChannel(int channel) {
        this.channel = channel;
    }

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


    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrumentNew) {
        instrument = instrumentNew;
    }

}
