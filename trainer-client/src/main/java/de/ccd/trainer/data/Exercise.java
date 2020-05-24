package de.ccd.trainer.data;

import lombok.Data;

import java.time.Duration;
import java.util.Collection;

@Data
public class Exercise {

    private final String id;
    private final String title;
    private final Duration duration;
    private final Collection<Attendee> attendees;

}
