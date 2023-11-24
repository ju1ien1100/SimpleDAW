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

        testSong = new Song("tester");
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

    @Test
    public void testRemoveMeasure() {
        testSong.addMeasure(0,1, testMeasure);
        assertEquals(testMeasure, testSong.getMeasures()[0][1]);
        testSong.removeMeasure(0,1);
        assertEquals(null, testSong.getMeasures()[0][1]);
    }

    @Test
    public void testPlayPauseSong() {
        testSong.addMeasure(0,0, testMeasure);
        assertFalse(testSong.getPlaying());
        testSong.pauseSong();
        assertFalse(testSong.getPlaying());
    }

    @Test
    public void testPopulatemeasures() {
        testSong.addMeasure(0, 0, testMeasure);
        assertNotNull(testSong.populateMeasures().get("piano"));
    }

    @Test
    public void testAddTrack() {
        testSong.addTrack();
        assertEquals(6, testSong.getMeasures().length);
    }

    @Test
    public void testAddMeasureColumn() {
        testSong.addMeasureColumn();
        assertEquals(9, testSong.getMeasures()[0].length);
    }

}