package de.ccd.attendee.data;

import lombok.Data;

import java.time.Instant;

@Data
public class Exercise {

    private final String id;
    private final String title;
    private final boolean Over;
    private final Instant endTime;

}
