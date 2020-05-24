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
    private final ExerciseView exerciseView = new ExerciseView();

    public void attendExercise(String username, String exerciseID) {
        exerciseStore.join(username, exerciseID);
        var exercise = exerciseStore.get(exerciseID);

        showExercise(username, exercise);
        observeExercise(exercise, exerciseView::display, App::exit);
    }

    @SneakyThrows
    public void showExercise(String username, Exercise exercise) {
        exerciseView.setOnSubmit(() -> exerciseStore.submit(username, exercise.getId()));
        exerciseView.display(exercise);
    }

    @SneakyThrows
    public void observeExercise(Exercise exercise, Consumer<Exercise> onUpdated, Runnable onFinished) {
        new Thread(() -> {
            while (true) {
                var updated = exerciseStore.get(exercise.getId());
                if (updated.isOver()) break;
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
