package sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.sound.MidiPlayer;

import static org.junit.jupiter.api.Assertions.*;

public class MidiPlayerTest {
    private MidiPlayer midi;

    @BeforeEach
    public void setUp() {
        midi = new MidiPlayer();
    }

    @Test
    public void testSelectInstrument() {
        assertEquals(0, midi.selectInstrument("piano"));
        assertEquals(38, midi.selectInstrument("synth"));
        assertEquals(0, midi.selectInstrument("julien"));
    }
}
