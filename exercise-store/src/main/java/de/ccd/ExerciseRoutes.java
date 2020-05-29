package de.ccd;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ccd.dto.Attendee;
import de.ccd.dto.Exercise;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ExerciseRoutes {

    private static Map<String, Exercise> exerciseMap = new HashMap<>();

    static {
        exerciseMap.put("a", Exercise.builder().id("a").title("foobar").build());
    }

    public static void start(HttpServerExchange exchange) {
        var body = Exchange.jsonBody(exchange);
        var title = body.get("title").asText();
        var duration = body.get("duration").asInt();
        var startTime = Instant.now();

        var exercise = Exercise.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .durationMinutes(duration)
                .startTime(startTime).build();
        exerciseMap.put(exercise.getId(), exercise);

        Exchange.sendJson(exchange, exercise);
    }

    public static void get(HttpServerExchange exchange) {
        var id = Exchange.pathParam(exchange, "id");
        var exercise = exerciseMap.get(id);

        Exchange.sendJson(exchange, exercise);
    }

    public static void attend(HttpServerExchange exchange) {
        var id = Exchange.pathParam(exchange, "id");
        var exercise = exerciseMap.get(id);
        var body = Exchange.jsonBody(exchange);
        var username = body.get("username").asText();

        var attendee = Attendee.builder()
                .name(username)
                .joinedTime(Instant.now())
                .build();
        exercise.add(attendee);

        Exchange.sendJson(exchange, attendee);
    }

    public static void complete(HttpServerExchange exchange) {
        var id = Exchange.pathParam(exchange, "id");
        var username = Exchange.pathParam(exchange, "username");
        var exercise = exerciseMap.get(id);
        var attendee = exercise.getAttendees().stream()
                .filter(a -> username.equals(a.getName()))
                .findFirst()
                .orElseThrow();
        attendee.setFinishedTime(Instant.now());

        var allCompleted = exercise.getAttendees().stream()
                .map(Attendee::getFinishedTime)
                .allMatch(Objects::nonNull);
        exercise.setFinished(allCompleted);

        Exchange.sendJson(exchange, exercise);
    }

}
