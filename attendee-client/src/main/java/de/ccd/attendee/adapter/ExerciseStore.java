package de.ccd.attendee.adapter;

import de.ccd.attendee.data.Exercise;

public interface ExerciseStore {

    Exercise get(String exerciseID);

    void join(String username, String exerciseID);

    void complete(String username, String exerciseID);
}
