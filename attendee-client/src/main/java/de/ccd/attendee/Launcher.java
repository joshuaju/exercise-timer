package de.ccd.attendee;

import de.ccd.attendee.adapter.ExerciseStore;
import de.ccd.attendee.adapter.ExerciseStoreHttp;
import de.ccd.attendee.ui.ExerciseView;
import org.apache.http.impl.client.HttpClients;

public class Launcher {

    public static void main(String[] args) {
        ExerciseStore exerciseStore = new ExerciseStoreHttp();
        ExerciseView exerciseView = new ExerciseView();

        var app = new App(exerciseStore, exerciseView);

        var username = args[0];
        var exerciseID = args[1];

        app.attendExercise(username, exerciseID);
    }

}
