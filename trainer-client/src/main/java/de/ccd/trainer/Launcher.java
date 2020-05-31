package de.ccd.trainer;

import de.ccd.trainer.adapter.ExerciseStore;
import de.ccd.trainer.adapter.ExerciseStoreHttp;
import org.apache.http.impl.client.HttpClients;

import java.time.Duration;

public class Launcher {

    public static void main(String[] args) {
        ExerciseStore exerciseStore = new ExerciseStoreHttp(HttpClients.createDefault());
        var app = new App(exerciseStore);

        var title = args[0];
        var duration = Duration.ofMinutes(Integer.parseInt(args[1]));

        app.superviseExercise(title, duration);
    }

}
