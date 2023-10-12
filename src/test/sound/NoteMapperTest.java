package sound;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.sound.NoteMapper;

import static org.junit.jupiter.api.Assertions.*;

public class NoteMapperTest {
    private NoteMapper noteChecker;

    @BeforeEach
    public void setUp() {
        noteChecker = new NoteMapper();
    }

    @Test
    public void midiTranslator() {
        assertEquals(60, noteChecker.getNoteValue("C"));
        assertEquals(61, noteChecker.getNoteValue("C#"));
        assertEquals(62, noteChecker.getNoteValue("D"));
        assertEquals(63, noteChecker.getNoteValue("D#"));
        assertEquals(64, noteChecker.getNoteValue("E"));
        assertEquals(65, noteChecker.getNoteValue("F"));
        assertEquals(66, noteChecker.getNoteValue("F#"));
        assertEquals(67, noteChecker.getNoteValue("G"));
        assertEquals(68, noteChecker.getNoteValue("G#"));
        assertEquals(69, noteChecker.getNoteValue("A"));
        assertEquals(70, noteChecker.getNoteValue("A#"));
        assertEquals(71, noteChecker.getNoteValue("B"));
        assertEquals(60, noteChecker.getNoteValue("nevermind"));
    }

}
