package de.ccd.trainer.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Exercise {

    private String id;
    private String title;
    private int duration;
    private Instant startTime;
    private Instant endTime;
    private boolean finished;
    private final Collection<Attendee> attendees = new ArrayList<>();

}
