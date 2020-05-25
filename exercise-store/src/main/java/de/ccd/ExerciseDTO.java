package de.ccd;

import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class ExerciseDTO {

    @NonNull
    private String id;
    @NonNull
    private String title;
    @NonNull
    private Duration duration;
    private Instant started = null;
    @NonNull
    private Collection<AttendeeDTO> attendeeDTOS;

}
