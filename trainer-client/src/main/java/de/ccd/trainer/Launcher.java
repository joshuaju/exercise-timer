package de.ccd.trainer;

import de.ccd.trainer.adapter.ExerciseStore;
import de.ccd.trainer.adapter.ExerciseStoreImpl;

import java.time.Duration;

public class Launcher {

    public static void main(String[] args) {
        ExerciseStore exerciseStore = new ExerciseStoreImpl();

        var app = new App(exerciseStore);

        var title = args[0];
        var duration = Duration.ofMinutes(Integer.parseInt(args[1]));

        app.superviseExercise(title, duration);
    }

}
