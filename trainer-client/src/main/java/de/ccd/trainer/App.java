package de.ccd.trainer;

import de.ccd.trainer.adapter.ExerciseStore;
import de.ccd.trainer.data.Exercise;
import de.ccd.trainer.ui.ExerciseView;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.Timer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class App {

    private final ExerciseStore exerciseStore;
    final ExerciseView exerciseView = new ExerciseView();

    public void superviseExercise(String title, Duration duration) {
        var exercise = exerciseStore.startExercise(title, duration);
        observeExercise(exercise, updated -> {
            if (updated.isFinished()){
                endExercise(updated);
            } else {
                exerciseView.display(updated);
            }
        });
        exerciseView.display(exercise);
    }

    public void observeExercise(Exercise exercise, Consumer<Exercise> onUpdated) {
        new Thread(() -> {
            while (true) {
                System.out.println("Updating exercise");
                var updated = exerciseStore.get(exercise.getId());
                onUpdated.accept(updated);
                sleep(500);
            }
        }).start();
    }

    public static void endExercise(Exercise exercise) {
        System.out.println("play sound");
        System.out.println("display stats");
        System.out.println("write protocol");
        App.exit();
    }

    private static void exit() {
        System.exit(0);
    }

    @SneakyThrows
    public static void sleep(long millis) {
        Thread.sleep(millis);
    }
}
