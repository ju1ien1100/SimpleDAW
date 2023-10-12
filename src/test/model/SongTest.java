package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    private Song testSong;
    private Measure testMeasure;
    private Note noteC;
    private Note noteD;

    @BeforeEach
    public void setUp() {
        testMeasure = new Measure("piano");
        noteC = new Note("C", 1000, 600);
        noteD = new Note("D", 1000, 600);
        testMeasure.addNote(noteC);
        testMeasure.addNote(noteD);

        testSong = new Song();
    }

    @Test
    public void testMeasuresInitialization() {
        assertNotNull(testSong.getMeasures());
        assertEquals(5, testSong.getMeasures().length);
        assertEquals(8, testSong.getMeasures()[0].length);
    }

    @Test
    public void testAddMeasure() {
        testSong.addMeasure(0,1, testMeasure);
        assertEquals(testMeasure, testSong.getMeasures()[0][1]);
    }




}