package de.ccd;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import lombok.SneakyThrows;

import java.util.Deque;
import java.util.Optional;

public class Exchange {

    @SneakyThrows
    public static JsonNode jsonBody(HttpServerExchange exchange) {
        var om = new ObjectMapper();
        return om.readTree(exchange.getInputStream());
    }

    public static String pathParam(HttpServerExchange exchange, String param) {
        return Optional.ofNullable(exchange.getQueryParameters().get(param))
                .map(Deque::getFirst)
                .orElseThrow();
    }

    @SneakyThrows
    public static void sendJson(HttpServerExchange exchange, Object object) {
        ObjectMapper mapper = new ObjectMapper();
        var jsonResponse = mapper.writeValueAsString(object);
        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(jsonResponse);
    }
}
