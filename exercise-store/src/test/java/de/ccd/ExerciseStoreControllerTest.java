package de.ccd;

import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


class ExerciseStoreControllerTest {

    @SneakyThrows
    public static void main(String[] args) {
        HttpPost post = new HttpPost("http://localhost:8080/exercises/");
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setEntity(new StringEntity("{\"title\":\"foobar\", \"duration\":\"PT11M\"}"));

        var client = HttpClients.createDefault();
        String body = client.execute(post, httpResponse -> {
            return EntityUtils.toString(httpResponse.getEntity());
        });

        System.out.println(body);
    }

}