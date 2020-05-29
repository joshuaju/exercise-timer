package de.ccd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exercise {

    private String id;
    private String title;
    private int durationMinutes;
    private Instant startTime;
    private Instant endTime;
    private boolean finished;
    private Collection<Attendee> attendees;

    public void add(Attendee attendee) {
        attendees.add(attendee);
    }
}
