package de.ccd.trainer.data;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Attendee {

    private String name;
    private Instant joinedTime;
    private Instant finishedTime;

}
