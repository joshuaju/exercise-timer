package de.ccd;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;

public class ExerciseServer {

    public static final int PORT = 8080;
    public static final String HOST = "0.0.0.0";

    public static final RoutingHandler ROUTES = new RoutingHandler()
            .post("exercise/", blocking(ExerciseRoutes::start))
            .get("exercise/{id}", blocking(ExerciseRoutes::get))
            .post("exercise/{id}/attendee", blocking(ExerciseRoutes::attend))
            .get("exercise/{id}/attendee/{username}/completion", blocking(ExerciseRoutes::complete));
            //.post("exercise/{id}/attendee/{username}/completion", blocking(ExerciseRoutes::complete)); // TODO make this POST again

    private static final HttpHandler blocking(HttpHandler next) {
        return new BlockingHandler(next);
    }

    public static void main(String[] args) {
        Undertow undertow = Undertow.builder()
                .addHttpListener(PORT, HOST, ROUTES)
                .build();
        undertow.start();
    }

}
