package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeasureTest {
    private Measure testMeasure;
    private Note noteC;
    private Note noteD;
    private Song testSong;

    @BeforeEach
    public void setUp() {
        testMeasure = new Measure("piano");
        noteC = new Note("C", 1000, 600);
        noteD = new Note("D", 1000, 600);
        testSong = new Song("tester");
    }

    @Test
    public void testAddNote() {
        testMeasure.addNote(noteC);
        assertEquals(1, testMeasure.getNotes().size());
        assertEquals(noteC, testMeasure.getNotes().get(0));

    }

    @Test
    public void testReplaceNote() {
        testMeasure.addNote(noteC);
        assertTrue(testMeasure.replaceNote(0, noteD));
        assertEquals(noteD, testMeasure.getNotes().get(0));
    }

    @Test
    public void testReplaceMultipleNote() {
        testMeasure.addNote(noteC);
        testMeasure.addNote(noteD);
        assertTrue(testMeasure.replaceNote(0, noteD));
        assertEquals(noteD, testMeasure.getNotes().get(0));
    }

    @Test
    public void testReplaceNoteFail() {
        testMeasure.addNote(noteC);
        assertFalse(testMeasure.replaceNote(1, noteD));
    }

    @Test
    public void testGetterSetter() {
        assertEquals("piano", testMeasure.getInstrument());
        testMeasure.setInstrument("guitar");
        assertEquals("guitar", testMeasure.getInstrument());
        testSong.addMeasure(0,0,testMeasure);
        assertEquals(0, testMeasure.getChannel());
    }

    @Test
    public void testGetStringNotes() {
        testMeasure.addNote(noteC);
        testMeasure.addNote(noteD);
        testMeasure.addNote(noteC);
        assertEquals("C", testMeasure.getStringNotes()[0]);
        assertEquals("D", testMeasure.getStringNotes()[1]);
        assertEquals("C", testMeasure.getStringNotes()[2]);
    }

    @Test
    public void testIsChord() {
        assertFalse(testMeasure.getIsChord());
        testMeasure.setIsChord();
        assertTrue(testMeasure.getIsChord());
    }
}
