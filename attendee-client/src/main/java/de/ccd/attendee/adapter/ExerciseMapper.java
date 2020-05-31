package de.ccd.attendee.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import de.ccd.attendee.data.Exercise;

import java.time.Duration;
import java.time.Instant;

public class ExerciseMapper {

    public static Exercise map(JsonNode json) {
        return Exercise.builder()
                .id(json.get("id").asText())
                .title(json.get("title").asText())
                .finished(json.get("finished").asBoolean())
                .endTime(calculateEndTime(json.get("duration").asInt(), json.get("startTime").asText()))
                .build();
    }

    private static Instant calculateEndTime(int duration, String started) {
        var startedTime = Instant.parse(started);
        return startedTime.plus(Duration.ofMinutes(duration));
    }

}
