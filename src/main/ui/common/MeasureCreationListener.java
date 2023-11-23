package ui.common;

import model.Measure;

//An interface for keeping track of measure that was made
public interface MeasureCreationListener {

    void onMeasureCreated(Measure measure);
}
