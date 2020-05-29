package de.ccd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exercise {

    private String id;
    private String title;
    private int duration;
    private Instant startTime;
    private Instant endTime;
    private boolean finished;
    private final Collection<Attendee> attendees = new ArrayList<>();

    public void add(Attendee attendee) {
        attendees.add(attendee);
    }
}
