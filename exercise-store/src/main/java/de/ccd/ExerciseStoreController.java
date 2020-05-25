package de.ccd;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ExerciseStoreController {

    private static final List<ExerciseDTO> EXERCISE_DTOS = new ArrayList<>();

    @GetMapping("/exercises")
    public List<ExerciseDTO> get() {
        return EXERCISE_DTOS;
    }

    @GetMapping("/exercises/{id}")
    public ExerciseDTO get(@PathVariable String id) {
        return EXERCISE_DTOS.stream()
                .filter(e -> id.equals(e.getId()))
                .findFirst()
                .orElseThrow();

    }

    @GetMapping("/exercises/{id}/start")
    public void start(@PathVariable String id) {
        var exercise = get(id);
        exercise.setStarted(Instant.now());
    }

    @GetMapping("/exercises/{id}/attend/{user}")
    public void attend(@PathVariable String id, @PathVariable String user) {
        var exercise = get(id);
        var attendee = new AttendeeDTO(user, Instant.now());
        if (exercise.getAttendeeDTOS().stream().map(AttendeeDTO::getName).noneMatch(user::equals))
            exercise.getAttendeeDTOS().add(attendee);
    }

    @GetMapping("/exercises/{id}/submit/{user}")
    public void submit(@PathVariable String id, @PathVariable String user) {
        var exercise = get(id);
        var attendee = exercise.getAttendeeDTOS().stream().filter(a -> user.equals(a.getName()))
                .findFirst()
                .orElseThrow();
        attendee.setFinishedTime(Instant.now());
    }

    @PostMapping("/exercises")
    public ExerciseDTO create(@RequestBody CreateExerciseRequest request) {
        var exercise = new ExerciseDTO(
                UUID.randomUUID().toString(),
                request.getTitle(),
                request.getDuration(),
                new ArrayList<>());
        EXERCISE_DTOS.add(exercise);
        return exercise;
    }

    @Data
    @NoArgsConstructor
    public static class CreateExerciseRequest {

        private String title;
        private Duration duration;
    }
}
