package de.ccd.trainer.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.ccd.trainer.data.Attendee;
import de.ccd.trainer.data.Exercise;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class AttendeeMapper {

    public static Attendee map(JsonNode atteneeNode) {
        var attendeeBuilder = Attendee.builder()
                .name(atteneeNode.get("name").asText())
                .joinedTime(Instant.parse(atteneeNode.get("joinedTime").asText()));
        Optional.ofNullable(atteneeNode.get("finishedTime").asText(null)).map(Instant::parse)
                .ifPresent(attendeeBuilder::finishedTime);
        return attendeeBuilder.build();
    }

}
