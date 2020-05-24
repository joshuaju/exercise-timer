package de.ccd.trainer.data;

import lombok.Data;

import java.time.Instant;

@Data
public class Attendee {

    private final String name;
    private final Instant joinedTime;

    private Instant finishedTime;

}
