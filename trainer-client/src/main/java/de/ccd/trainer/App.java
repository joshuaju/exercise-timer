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
        startCountdown(exercise);
        observeExercise(exercise, updated -> allSubmitted(updated,
                () -> endExercise(exercise),
                () -> exerciseView.display(exercise)));
        exerciseView.display(exercise);
    }

    public void startCountdown(Exercise exercise) {
        new Thread(() -> {
            System.out.println("Starting countdown" + exercise.getDuration().toMillis());
            sleep(exercise.getDuration().toMillis());
            System.out.println("Time up.");
            endExercise(exercise);
        }).start();
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

    public void allSubmitted(Exercise exercise, Runnable onAllSubmitted, Runnable onElse) {
        System.out.println("All submitted? -> Always NO");
        onElse.run(); // TODO
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
