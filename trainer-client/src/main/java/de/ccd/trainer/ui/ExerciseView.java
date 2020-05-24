package de.ccd.trainer.ui;

import de.ccd.trainer.data.Attendee;
import de.ccd.trainer.data.Exercise;

import javax.swing.*;
import java.awt.*;

public class ExerciseView extends JFrame {

    private final JLabel lblTitle;
    private final JLabel lblDuration;
    private final JList<Attendee> lvAttendees;

    public ExerciseView() {
        super("Trainer");
        lblTitle = new JLabel();
        lblDuration = new JLabel();
        lvAttendees = new JList<>();

        var panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));

        panel.add(new JLabel("Title"));
        panel.add(lblTitle);
        panel.add(new JLabel("Duration"));
        panel.add(lblDuration);
        panel.add(new JLabel("Attendees"));
        panel.add(lvAttendees);

        lvAttendees.setCellRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value.getName() + ", " + value.getJoinedTime().toString() + ", " + value.getFinishedTime()));

        getContentPane().add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void display(Exercise exercise) {
        lblTitle.setText(exercise.getTitle());
        lblDuration.setText(exercise.getDuration() + " minutes");

        DefaultListModel<Attendee> model = new DefaultListModel<>();
        model.addAll(exercise.getAttendees());
        lvAttendees.setModel(model);

        this.pack();
    }

}
