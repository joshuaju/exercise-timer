package de.ccd.attendee.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import de.ccd.AttendeeDTO;
import de.ccd.ExerciseDTO;
import de.ccd.attendee.data.Exercise;
import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Objects;
import java.util.Optional;

public class ExerciseStoreImpl implements ExerciseStore {

    private static final String base = "http://localhost:8080";

    @Override
    @SneakyThrows
    public void join(String username, String exerciseID) {
        var client = HttpClients.createDefault();
        var request = new HttpGet(String.format(base + "/exercises/%s/attend/%s", exerciseID, username));
        client.execute(request);

    }

    @Override
    @SneakyThrows
    public void submit(String username, String exerciseID) {
        var client = HttpClients.createDefault();
        var request = new HttpGet(String.format(base + "/exercises/%s/submit/%s", exerciseID, username));
        client.execute(request);
    }

    @Override
    @SneakyThrows
    public Exercise get(String exerciseID) {
        var client = HttpClients.createDefault();
        var request = new HttpGet(String.format(base + "/exercises/%s", exerciseID));

        var dto = client.execute(request, response -> {
            var entity = response.getEntity();
            var json = EntityUtils.toString(entity);
            var om = new ObjectMapper();
            om.findAndRegisterModules();
            return om.readValue(json, ExerciseDTO.class);
        });

        var allFinished = dto.getAttendeeDTOS().stream()
                .map(AttendeeDTO::getFinishedTime)
                .allMatch(Objects::nonNull) && !dto.getAttendeeDTOS().isEmpty();

        var endTime = Optional.ofNullable(dto.getStarted())
                .map(started -> started.plus(dto.getDuration()))
                .orElse(null);

        return new Exercise(
                dto.getId(),
                dto.getTitle(),
                allFinished,
                endTime);

    }

    public static void main(String[] args) {
        var store = new ExerciseStoreImpl();
        String exerciseID = "360faf99-6ba2-48cb-9855-b33de6fd797f";
        var e = store.get(exerciseID);

        store.join("john", exerciseID);
        var e1 = store.get(exerciseID);

        store.submit("john", exerciseID);
        var e2 = store.get(exerciseID);

        int i = 1;
    }
}
