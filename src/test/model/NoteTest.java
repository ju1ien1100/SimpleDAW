package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteTest {
    private Note noteC;
    private Note blankNote;

    @BeforeEach
    public void setUp() {
        noteC = new Note("C", 1000, 600);
        blankNote = new Note("", 1000, 600);
    }

    @Test
    public void getterTest() {
        assertEquals("C", noteC.getPitch());
        assertEquals(1000, noteC.getDuration());
        assertEquals(600, noteC.getVelocity());
    }



}
