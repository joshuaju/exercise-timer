package de.ccd;

import de.ccd.dto.Attendee;
import de.ccd.dto.Exercise;
import de.ccd.util.Exchange;
import io.undertow.server.HttpServerExchange;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ExerciseRoutes {

    private static final Map<String, Exercise> exerciseMap = new HashMap<>();

    static {
        exerciseMap.put("a", Exercise.builder().id("a").title("foobar").build());
    }

    private static int ID = 0;

    public static void start(HttpServerExchange exchange) {
        var body = Exchange.jsonBody(exchange);
        var title = body.get("title").asText();
        var duration = body.get("duration").asInt();
        var startTime = Instant.now();

        var exercise = Exercise.builder()
                .id("" + ID++)
                .title(title)
                .duration(duration)
                .startTime(startTime).build();
        exerciseMap.put(exercise.getId(), exercise);

        Exchange.sendJson(exchange, exercise);
        System.out.println("Started " + exercise.getId());
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
        System.out.println("User " + username + " attends " + id);
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

        if (allCompleted) {
            exercise.setEndTime(Instant.now());
        }
        exercise.setFinished(allCompleted);

        Exchange.sendJson(exchange, exercise);
        System.out.println("User " + username + " completes " + id);
    }

}
