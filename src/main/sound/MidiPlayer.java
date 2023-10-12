package sound;

import model.*;
import javax.sound.midi.*;
import java.util.List;


//NOTE that this was derived from the midiplayer presented to students
// from the course content
public class MidiPlayer {
    private Synthesizer synth;
    private MidiChannel[] channels;
    private Instrument[] instruments;


    //Effects:Loads and opens synthesizers and gets instruments + channels
    //
    public MidiPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            instruments = synth.getDefaultSoundbank().getInstruments();
            channels = synth.getChannels();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    //REQUIRES: the name of a real instrument
    //MODIFIES: synth
    //EFFECTS: loads and saves an instrument into the synth if valid, returns the int representation of instrument
    public int selectInstrument(String instrumentName) {
        for (Instrument instrument : instruments) {
            if (instrument.getName().toLowerCase().contains(instrumentName.toLowerCase())) {
                synth.loadInstrument(instrument);
                //channels[0].programChange(instrument.getPatch().getProgram());
                return instrument.getPatch().getProgram();
            }
        }
        return -69;
    }

    //REQUIRES: a positive trackChannel, a valid instrument, non null list of Notes
    //EFFECT: Handles the playing and stoping of a melody for a given instrument
    public void playNotes(int trackChannel, String instrumentName, List<Note> melody) {
        channels[trackChannel].programChange(selectInstrument(instrumentName));

        for (Note note : melody) {
            int noteValue = NoteMapper.getNoteValue(note.getPitch());
            channels[trackChannel].noteOn(noteValue, note.getVelocity());
            try {
                Thread.sleep(note.getDuration());
            } catch (InterruptedException e) {
                channels[trackChannel].noteOff(noteValue);
            }
            channels[trackChannel].noteOff(noteValue);
        }
    }

    public void playChord(int trackChannel, String instrumentName, List<Note> notes) {
        channels[trackChannel].programChange(selectInstrument(instrumentName));

        for (Note note : notes) {
            channels[trackChannel].noteOn(NoteMapper.getNoteValue(note.getPitch()), note.getVelocity());
        }

        for (Note note : notes) {
            channels[trackChannel].noteOff(NoteMapper.getNoteValue(note.getPitch()));
        }


    }

    public void close() {
        synth.close();
    }
}