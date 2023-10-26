package persistence;

import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private static final String TEST_FILE = "testSong.json";
    private JsonWriter jsonWriter;
    private Song song;
    private Measure testMeasure;
    private Note noteC;
    private Note noteD;

    @BeforeEach
    void setUp() {
        jsonWriter = new JsonWriter(TEST_FILE);
        testMeasure = new Measure("piano");
        noteC = new Note("C", 1000, 600);
        noteD = new Note("D", 1000, 600);
        testMeasure.addNote(noteC);
        testMeasure.addNote(noteD);
        song = new Song("TestSong");
        song.addMeasure(0,0, testMeasure);

    }

    @Test
    void testConstructor() {
        assertNotNull(jsonWriter);
    }

    @Test
    void testOpen() {
        try {
            jsonWriter.open();
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        }
    }

    @Test
    void testOpenFileNotFoundException() {
        JsonWriter invalidWriter = new JsonWriter("invalid" + File.separator + "path.json");
        assertThrows(FileNotFoundException.class, invalidWriter::open);
    }

    @Test
    void testWrite() {
        Map<String, Song> songList = new HashMap<>();


        songList.put("Test", song);

        try {
            jsonWriter.open();
            jsonWriter.write(songList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        }
    }







}
