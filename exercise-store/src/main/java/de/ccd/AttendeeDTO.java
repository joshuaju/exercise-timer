package de.ccd;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class AttendeeDTO {

    @NonNull
    private String name;
    @NonNull
    private Instant joinedTime;
    private Instant finishedTime;

}
