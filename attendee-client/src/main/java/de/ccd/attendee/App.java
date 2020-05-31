package de.ccd.attendee;

import de.ccd.attendee.adapter.ExerciseStore;
import de.ccd.attendee.data.Exercise;
import de.ccd.attendee.ui.ExerciseView;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class App {

    private final ExerciseStore exerciseStore;
    private final ExerciseView exerciseView;

    public void attendExercise(String username, String exerciseID) {
        var exercise = joinExercise(username, exerciseID);
        showExercise(username, exercise);
        observeExercise(exercise, exerciseView::display, App::exit);
    }

    public Exercise joinExercise(String username, String exerciseId) {
        exerciseStore.join(username, exerciseId);
        return exerciseStore.get(exerciseId);
    }

    @SneakyThrows
    public void showExercise(String username, Exercise exercise) {
        exerciseView.setOnSubmit(() -> exerciseStore.complete(username, exercise.getId()));
        exerciseView.display(exercise);
    }

    @SneakyThrows
    public void observeExercise(Exercise exercise, Consumer<Exercise> onUpdated, Runnable onFinished) {
        new Thread(() -> {
            while (true) {
                var updated = exerciseStore.get(exercise.getId());
                if (updated.isFinished()) break;
                else onUpdated.accept(updated);
                sleep();
            }
            onFinished.run();
        }).start();
    }

    public static void exit() {
        System.exit(0);
    }

    @SneakyThrows
    public static void sleep() {
        Thread.sleep(500);
    }
}
