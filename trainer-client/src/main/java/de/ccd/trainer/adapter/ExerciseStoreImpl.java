package de.ccd.trainer.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.ccd.trainer.data.Attendee;
import de.ccd.trainer.data.Exercise;
import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExerciseStoreImpl implements ExerciseStore {

    private static final String base = "http://localhost:8080";

    @Override
    @SneakyThrows
    public Exercise startExercise(String title, Duration duration) {
        var client = HttpClients.createDefault();

        var post = new HttpPost(base + "/exercise/");

        String requestBody = String.format("{ \"title\" : \"%s\", \"duration\" : %d }", title, duration.toMinutes());
        StringEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        post.setEntity(entity);

        return client.execute(post, response -> {
            var content = response.getEntity().getContent();
            var om = new ObjectMapper();
            var json = om.readTree(content);
            return map(json);
        });
    }


    @Override
    @SneakyThrows
    public Exercise get(String exerciseID) {
        var client = HttpClients.createDefault();
        var request = new HttpGet(String.format(base + "/exercise/%s", exerciseID));

        return client.execute(request, response -> {
            var content = response.getEntity().getContent();
            var om = new ObjectMapper();
            var json = om.readTree(content);
            return map(json);
        });

    }

    private Exercise map(JsonNode json) {
        var builder = Exercise.builder();
        builder.id(json.get("id").asText())
                .title(json.get("title").asText())
                .duration(json.get("duration").asInt())
                .startTime(Instant.parse(json.get("startTime").asText()))
                .finished(json.get("finished").asBoolean(false));

        Optional.ofNullable(json.get("endTime").asText(null))
                .map(Instant::parse)
                .ifPresent(builder::endTime);

        Exercise exercise = builder.build();
        var attendees = (ArrayNode) json.withArray("attendees");
        attendees.forEach(atteneeNode -> {
            var attendeeBuilder = Attendee.builder()
                    .name(atteneeNode.get("name").asText())
                    .joinedTime(Instant.parse(atteneeNode.get("joinedTime").asText()));
            Optional.ofNullable(atteneeNode.get("finishedTime").asText(null))
                    .map(Instant::parse)
                    .ifPresent(attendeeBuilder::finishedTime);
            exercise.getAttendees().add(attendeeBuilder.build());
        });
        return exercise;
    }


}
