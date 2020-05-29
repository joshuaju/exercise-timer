package de.ccd.attendee;

import de.ccd.attendee.adapter.ExerciseStore;
import de.ccd.attendee.adapter.ExerciseStoreImpl;
import de.ccd.attendee.data.Exercise;

import java.time.Duration;
import java.time.Instant;

public class Launcher {

    public static void main(String[] args) {
        ExerciseStore exerciseStore = new ExerciseStoreImpl();

        var app = new App(exerciseStore);

        var username = args[0];
        var exerciseID = args[1];

        app.attendExercise(username, exerciseID);
    }

}
