package de.ccd.attendee;

import de.ccd.attendee.adapter.ExerciseStore;
import de.ccd.attendee.data.Exercise;

import java.time.Duration;
import java.time.Instant;

public class Launcher {

    public static void main(String[] args) {
        ExerciseStore exerciseStore = new ExerciseStore() {
            private int countdown = 10;

            @Override
            public void join(String username, String exerciseID) {
                System.out.println(username + " joined " + exerciseID);
            }

            @Override
            public void submit(String username, String exerciseID) {
                System.out.println(username + " submitted " + exerciseID);
            }

            @Override
            public Exercise get(String exerciseID) {
                System.out.println("Getting " + exerciseID);
                return new Exercise(exerciseID, "Default Title", countdown-- == 0, Instant.now().plus(Duration.ofMinutes(1)));
            }
        }; // TODO

        var app = new App(exerciseStore);

        var username = args[0];
        var exerciseID = args[1];

        app.attendExercise(username, exerciseID);
    }

}
