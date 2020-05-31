package de.ccd.attendee.ui;

import de.ccd.attendee.data.Exercise;

import javax.swing.*;
import java.awt.*;


public class ExerciseView extends JFrame {

    private final JLabel lblID;
    private final JLabel lblTitle;
    private final JLabel lblEnding;
    private final JButton btnSubmit;

    public ExerciseView() {
        super("Attendee");
        lblID = new JLabel();
        lblTitle = new JLabel();
        lblEnding = new JLabel();
        this.btnSubmit = new JButton("Submit");

        var panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));

        panel.add(new JLabel("ID"));
        panel.add(lblID);
        panel.add(new JLabel("Title"));
        panel.add(lblTitle);
        panel.add(new JLabel("Ending"));
        panel.add(lblEnding);
        panel.add(btnSubmit);

        getContentPane().add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setOnSubmit(Runnable runnable) {
        btnSubmit.addActionListener(e -> runnable.run());
    }

    public void display(Exercise exercise) {
        lblID.setText(exercise.getId());
        lblTitle.setText(exercise.getTitle());
        lblEnding.setText(exercise.getEndTime().toString());
        this.pack();
    }

}
