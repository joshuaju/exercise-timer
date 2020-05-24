package de.ccd.trainer;

import de.ccd.trainer.adapter.ExerciseStore;
import de.ccd.trainer.data.Attendee;
import de.ccd.trainer.data.Exercise;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        ExerciseStore exerciseStore = new ExerciseStore() {

            @Override
            public Exercise startExercise(String title, Duration duration) {
                Attendee peter = new Attendee("Peter", Instant.now());
                peter.setFinishedTime(peter.getJoinedTime().plus(Duration.ofSeconds(2)));
                Attendee josh = new Attendee("Josh", Instant.now());
                return new Exercise("0", title, Duration.ofSeconds(10), List.of(peter, josh));
            }

            @Override
            public Exercise get(String id) {
                Attendee peter = new Attendee("Peter", Instant.now());
                peter.setFinishedTime(peter.getJoinedTime().plus(Duration.ofSeconds(2)));
                Attendee josh = new Attendee("Josh", Instant.now());
                return new Exercise("0", "default", Duration.ofSeconds(10), List.of(peter, josh));
            }
        }; // TODO

        var app = new App(exerciseStore);

        var title = args[0];
        var duration = Duration.ofSeconds(Integer.parseInt(args[1]));

        app.superviseExercise(title, duration);
    }

}
