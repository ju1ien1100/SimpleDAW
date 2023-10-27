package persistence;

import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.HashMap;

import org.json.*;

// A class to read a json representation of a song and turn it into a song class
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads song from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HashMap<String, Song> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSongList(jsonObject);
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private HashMap<String, Song> parseSongList(JSONObject jsonObject) {
        HashMap<String, Song> songList = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            try {
                JSONObject songObject = jsonObject.getJSONObject(key);
                Song song = parseSong(songObject);
                songList.put(key, song);
            } catch (JSONException e) {
                System.err.println("Error parsing song: " + key);
            }
        }
        return songList;
    }

    // EFFECTS: parses song from JSON object and returns it
    private Song parseSong(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int bpm = jsonObject.getInt("bpm");
        Song song = new Song(name);
        song.setBpm(bpm);  // Assuming there's a setBpm method in Song class
        addTracks(song, jsonObject);
        return song;
    }

    // MODIFIES: song
    // EFFECTS: parses tracks from JSON object and adds them to song
    private void addTracks(Song song, JSONObject jsonObject) {
        JSONArray tracksArray = jsonObject.getJSONArray("tracks");
        for (int i = 0; i < tracksArray.length(); i++) {
            JSONArray measuresArray = tracksArray.getJSONArray(i);
            for (int j = 0; j < measuresArray.length(); j++) {
                if (!measuresArray.isNull(j)) {
                    JSONObject measureObject = measuresArray.getJSONObject(j);
                    Measure measure = parseMeasure(measureObject);
                    song.addMeasure(i, j, measure);
                }
            }
        }
    }

    // EFFECTS: parses measure from JSON object and returns it
    private Measure parseMeasure(JSONObject jsonObject) {
        String instrument = jsonObject.getString("instrument");
        int channel = jsonObject.getInt("channel");
        boolean isChord = jsonObject.getBoolean("isChord");
        Measure measure = new Measure(instrument);
        measure.setChannel(channel);
        if (isChord) {
            measure.setIsChord();
        } else {
            measure.setIsNotChord();
        }
        addNotes(measure, jsonObject);
        return measure;
    }

    // MODIFIES: measure
    // EFFECTS: parses notes from JSON object and adds them to measure
    private void addNotes(Measure measure, JSONObject jsonObject) {
        JSONArray notesArray = jsonObject.getJSONArray("notes");
        for (Object json : notesArray) {
            JSONObject nextNote = (JSONObject) json;
            addNote(measure, nextNote);
        }
    }

    // MODIFIES: measure
    // EFFECTS: parses note from JSON object and adds it to measure
    private void addNote(Measure measure, JSONObject jsonObject) {
        String pitch = jsonObject.getString("pitch");
        int duration = jsonObject.getInt("duration");
        int velocity = jsonObject.getInt("velocity");
        Note note = new Note(pitch, duration, velocity);
        measure.addNote(note);
    }


}
