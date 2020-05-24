package de.ccd.trainer.adapter;

import de.ccd.trainer.data.Exercise;

import java.time.Duration;

public interface ExerciseStore {

    Exercise startExercise(String title, Duration duration);

    Exercise get(String id);
}
