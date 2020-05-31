package de.ccd.attendee.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ccd.attendee.data.Exercise;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

public class ExerciseStoreHttp implements ExerciseStore {


    private static final String base = "http://localhost:8080";

    private static final ResponseHandler<Exercise> exerciseRespone = response -> {
        var content = response.getEntity().getContent();
        var om = new ObjectMapper();
        var json = om.readTree(content);
        return ExerciseMapper.map(json);
    };

    @Override
    @SneakyThrows
    public void join(String username, String exerciseID) {
        var client = HttpClients.createDefault();
        var post = new HttpPost(String.format(base + "/exercise/%s/attendee/", exerciseID));
        String json = "{ \"username\" : \"" + username + "\"}";
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        client.execute(post);
    }

    @Override
    @SneakyThrows
    public void complete(String username, String exerciseID) {
        var client = HttpClients.createDefault();
        var request = new HttpPost(String.format(base + "/exercise/%s/attendee/%s/completion", exerciseID, username));
        client.execute(request);
    }

    @Override
    @SneakyThrows
    public Exercise get(String exerciseID) {
        var client = HttpClients.createDefault();
        var request = new HttpGet(String.format(base + "/exercise/%s", exerciseID));
        return client.execute(request, exerciseRespone);

    }


}
