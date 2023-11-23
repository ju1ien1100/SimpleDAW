package ui.components;

import model.Measure;
import model.Note;
import ui.common.MeasureCreationListener;
import ui.sound.NoteMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

//A class to make measures
public class MeasureView extends  JDialog {


    private final List<Note> notesInMeasure;
    private final DefaultListModel<String> notesListModel;
    private MeasureCreationListener creationListener;

    public MeasureView(Frame owner, MeasureCreationListener listener) {
        super(owner, "Create Measure", true);
        this.creationListener = listener;
        notesInMeasure = new ArrayList<>();
        notesListModel = new DefaultListModel<>();
        initializeUI();
    }

    //makes the UI of the notes and the buttons
    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel notesPanel = new JPanel(new GridLayout(0, NoteMapper.NOTE_VALUES.size()));
        for (String noteName : NoteMapper.NOTE_VALUES.keySet()) {
            JButton noteButton = new JButton(noteName);
            noteButton.addActionListener(e -> addNoteAction(noteName));
            notesPanel.add(noteButton);
        }

        JList<String> notesList = new JList<>(notesListModel);
        JScrollPane scrollPane = new JScrollPane(notesList);
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> finalizeMeasure());

        add(notesPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(doneButton, BorderLayout.SOUTH);

        setSize(750, 300);
    }

    //EFFECTS: action for adding a note
    private void addNoteAction(String noteName) {
        String durationStr = JOptionPane.showInputDialog("Enter duration for " + noteName + ":");
        String velocityStr = JOptionPane.showInputDialog("Enter velocity for " + noteName + ":");

        try {
            int duration = Integer.parseInt(durationStr);
            int velocity = Integer.parseInt(velocityStr);
            Note note = new Note(noteName, duration, velocity);
            notesInMeasure.add(note);
            notesListModel.addElement(noteName + " Duration: " + duration + " Velocity: " + velocity);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for duration and velocity.");
        }
    }

    //EFFECTS: Finishing the measure by selecting instrument and adding
    // to measureBasket()
    private Measure finalizeMeasure() {

        String[] instruments = {"piano", "violin", "organ", "synth"};
        String instrument = (String) JOptionPane.showInputDialog(
                null,
                "What instrument do you want:",
                "Select Instrument",
                JOptionPane.QUESTION_MESSAGE,
                null,
                instruments,
                instruments[0]
        );

        if (instrument != null && !instrument.isEmpty()) {
            Measure measure = new Measure(instrument);

            for (Note note: notesInMeasure) {
                measure.addNote(note);
            }
            if (creationListener != null) {
                creationListener.onMeasureCreated(measure);
            }
            dispose();
            return measure;

        }

        setVisible(false);
        return null;
    }


}


