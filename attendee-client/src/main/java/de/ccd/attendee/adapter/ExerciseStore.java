package de.ccd.attendee.adapter;

import de.ccd.attendee.data.Exercise;

public interface ExerciseStore {

    void join(String username, String exerciseID);

    void submit(String username, String exerciseID);

    Exercise get(String exerciseID);
}
