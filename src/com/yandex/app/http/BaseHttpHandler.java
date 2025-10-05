package com.yandex.app.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BaseHttpHandler {
    protected static final Gson gson = new Gson();

    protected void sendText(HttpExchange exchange, String text, int statusCode) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(statusCode, resp.length);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendSuccess(HttpExchange exchange, String text) throws IOException {
        sendText(exchange, text, 200);
    }

    protected void sendCreated(HttpExchange exchange, String text) throws IOException {
        sendText(exchange, text, 201);
    }

    protected void sendNotFound(HttpExchange exchange) throws IOException {
        sendText(exchange, "{\"error\":\"Not Found\"}", 404);
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        sendText(exchange, "{\"error\":\"Task time overlaps with existing tasks\"}", 406);
    }

    protected void sendInternalError(HttpExchange exchange) throws IOException {
        sendText(exchange, "{\"error\":\"Internal Server Error\"}", 500);
    }

    protected void sendBadRequest(HttpExchange exchange, String message) throws IOException {
        sendText(exchange, "{\"error\":\"" + message + "\"}", 400);
    }

    protected <T> Optional<T> parseJson(HttpExchange exchange, Class<T> clazz) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        try {
            T obj = gson.fromJson(body, clazz);
            return Optional.ofNullable(obj);
        } catch (JsonSyntaxException e) {
            return Optional.empty();
        }
    }
}