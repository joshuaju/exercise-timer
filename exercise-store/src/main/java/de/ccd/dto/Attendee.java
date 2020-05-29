package de.ccd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendee {

    private String name;
    private Instant joinedTime;
    private Instant finishedTime;

}
