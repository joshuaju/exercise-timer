package de.ccd;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;

public class ExerciseServer {

    public static final RoutingHandler ROUTES = new RoutingHandler()
            .post("exercise/", ExerciseRoutes::start)
            .get("exercise/{id}", ExerciseRoutes::get)
            .post("exercise/{id}/attendee", ExerciseRoutes::attend)
            .post("exercise({id}/attendee/{username}/completion", ExerciseRoutes::complete);

    public static void main(String[] args) {
        Undertow undertow = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0", ROUTES)
                .build();
        undertow.start();
    }

}
