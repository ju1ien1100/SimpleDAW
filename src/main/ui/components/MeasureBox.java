package ui.components;


import java.awt.*;
import ui.common.SoundBox;
import model.Measure;

//Represents a soundbox for a measure
public class MeasureBox extends SoundBox {

    private Measure measure;

    //constructor
    public MeasureBox(long duration, long height, Color color, Measure measure) {
        super(duration, height, color);

        this.measure = measure;
    }

    public Measure getMeasure() {
        return measure;
    }
}
