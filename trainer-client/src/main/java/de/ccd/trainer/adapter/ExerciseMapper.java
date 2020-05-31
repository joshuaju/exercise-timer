package de.ccd.trainer.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.ccd.trainer.data.Attendee;
import de.ccd.trainer.data.Exercise;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class ExerciseMapper {

    public static Exercise map(JsonNode json) {
        var exerciseBuilder = Exercise.builder();
        exerciseBuilder.id(json.get("id").asText())
                .title(json.get("title").asText())
                .duration(json.get("duration").asInt())
                .startTime(Instant.parse(json.get("startTime").asText()))
                .finished(json.get("finished").asBoolean(false));
        Optional.ofNullable(json.get("endTime").asText(null))
                .map(Instant::parse)
                .ifPresent(exerciseBuilder::endTime);
        Exercise exercise = exerciseBuilder.build();

        var attendees = (ArrayNode) json.withArray("attendees");
        StreamSupport.stream(attendees.spliterator(), false)
                .map(AttendeeMapper::map)
                .forEach(exercise.getAttendees()::add);

        return exercise;
    }

}
