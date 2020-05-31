package de.ccd.trainer;

import de.ccd.trainer.adapter.ExerciseStore;
import de.ccd.trainer.data.Exercise;
import de.ccd.trainer.ui.ExerciseView;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class App {

    private final ExerciseStore exerciseStore;
    final ExerciseView exerciseView = new ExerciseView();

    public void superviseExercise(String title, Duration duration) {
        var exercise = exerciseStore.startExercise(title, duration);
        exerciseView.display(exercise);
        observeExercise(exercise, this::refresh);
    }

    public void refresh(Exercise updated) {
        if (updated.isFinished()){
            endExercise(updated);
        } else {
            exerciseView.display(updated);
        }
    }

    public static void endExercise(Exercise exercise) {
        System.out.println("play sound");
        System.out.println("display stats");
        System.out.println("write protocol");
        App.exit();
    }

    private void observeExercise(Exercise exercise, Consumer<Exercise> onUpdated) {
        new Thread(() -> {
            while (true) {
                var updated = exerciseStore.get(exercise.getId());
                onUpdated.accept(updated);
                sleep(500);
            }
        }).start();
    }

    private static void exit() {
        System.exit(0);
    }

    @SneakyThrows
    private static void sleep(long millis) {
        Thread.sleep(millis);
    }
}
