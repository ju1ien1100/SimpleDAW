package persistence;

import model.*;
import org.json.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private JsonReader jsonReader;

    @BeforeEach
    void setUp() {
        jsonReader =  new JsonReader("./data/songList.json");
    }

    @Test
    void testConstructor() {
        assertNotNull(jsonReader);
    }

    @Test
    void testRead() {
        try {
            HashMap<String, Song> songList = jsonReader.read();
            assertNotNull(songList);

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testReadIOException() {
        jsonReader = new JsonReader("badpath.json");
        assertThrows(IOException.class, jsonReader::read);
    }

}
