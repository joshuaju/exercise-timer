package de.ccd.trainer.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Attendee {

    private String name;
    private Instant joinedTime;
    private Instant finishedTime;

}
