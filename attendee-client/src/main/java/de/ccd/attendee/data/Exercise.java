package de.ccd.attendee.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Exercise {

    private String id;
    private String title;
    private boolean finished;
    private Instant endTime;

}
