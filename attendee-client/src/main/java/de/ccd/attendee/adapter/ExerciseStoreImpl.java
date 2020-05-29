package de.ccd.attendee.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ccd.attendee.data.Exercise;
import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.time.Duration;
import java.time.Instant;

public class ExerciseStoreImpl implements ExerciseStore {

    private static final String base = "http://localhost:8080";

    @Override
    @SneakyThrows
    public void join(String username, String exerciseID) {
        var post = new HttpPost(String.format(base + "/exercise/%s/attendee/", exerciseID));

        String json = "{ \"username\" : \"" + username + "\"}";
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);

        var client = HttpClients.createDefault();
        client.execute(post);
    }

    @Override
    @SneakyThrows
    public void complete(String username, String exerciseID) {
        var client = HttpClients.createDefault();
        var request = new HttpPost(String.format(base + "/exercises/%s/attendee/%s/completion", exerciseID, username));
        client.execute(request);
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
        return Exercise.builder()
                .id(json.get("id").asText())
                .title(json.get("title").asText())
                .finished(json.get("finished").asBoolean())
                .endTime(determineEndTime(json.get("duration").asInt(), json.get("startTime").asText()))
                .build();
    }

    private Instant determineEndTime(int duration, String started) {
        var startedTime = Instant.parse(started);
        return startedTime.plus(Duration.ofMinutes(duration));
    }
}
