package persistence;

import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private static final String TEST_FILE = "./data/mainSong.json";
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
            JsonWriter writer = new JsonWriter("./data/testWrite.json");

            writer.open();
            writer.write(songList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWrite.json");
            songList = reader.read();
            assertNotNull(songList);
            assertEquals("TestSong", songList.get("Test").getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testClose() {
        try {
            jsonWriter.open();
            jsonWriter.close();
            // Here, you might want to test if the file has actually been closed. This could involve checking if operations on the file throw expected exceptions, for example.

        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        }
    }
}
