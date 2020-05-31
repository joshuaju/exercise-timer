package de.ccd.trainer.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ccd.trainer.data.Exercise;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.time.Duration;

@RequiredArgsConstructor
public class ExerciseStoreHttp implements ExerciseStore {

    private final CloseableHttpClient client;

    private static final String base = "http://localhost:8080";

    private final static ResponseHandler<Exercise> exerciseResponse = (response) -> {
        var content = response.getEntity().getContent();
        var om = new ObjectMapper();
        var json = om.readTree(content);
        return ExerciseMapper.map(json);
    };

    @Override
    @SneakyThrows
    public Exercise startExercise(String title, Duration duration) {
        var post = new HttpPost(base + "/exercise/");
        String requestBody = String.format("{ \"title\" : \"%s\", \"duration\" : %d }", title, duration.toMinutes());
        StringEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        return client.execute(post, exerciseResponse);
    }


    @Override
    @SneakyThrows
    public Exercise get(String exerciseID) {
        var request = new HttpGet(String.format(base + "/exercise/%s", exerciseID));
        return client.execute(request, exerciseResponse);

    }


}
