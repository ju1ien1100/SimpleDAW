package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import ui.common.SoundBox;
import model.Measure;

//Represents a soundbox for a measure
public class MeasureBox extends SoundBox {

    private Measure measure;

    public MeasureBox(long duration, long height, Color color, Measure measure) {
        super(duration, height, color);

        this.measure = measure;
    }

    public Measure getMeasure() {
        return measure;
    }
}
