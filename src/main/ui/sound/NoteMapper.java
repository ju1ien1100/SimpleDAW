package ui.sound;

import java.util.HashMap;
import java.util.Map;

public class NoteMapper {

    private static final Map<String, Integer> NOTE_VALUES = new HashMap<String, Integer>() {
        {
            put("C", 60);
            put("C#", 61);
            put("D", 62);
            put("D#", 63);
            put("E", 64);
            put("F", 65);
            put("F#", 66);
            put("G", 67);
            put("G#", 68);
            put("A", 69);
            put("A#", 70);
            put("B", 71);
        }
    };

    //Note: returns C if not found
    //EFFECTS: returns the note value coresponding to the char representation of the note
    public static int getNoteValue(String note) {
        return NOTE_VALUES.getOrDefault(note, 60);
    }


}
