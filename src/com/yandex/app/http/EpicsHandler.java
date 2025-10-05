package com.yandex.app.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Epic;
import com.yandex.app.service.TaskManager;
import java.io.IOException;
import java.util.Optional;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;

    public EpicsHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            switch (method) {
                case "GET":
                    handleGet(exchange, path);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange, path);
                    break;
                default:
                    sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }

    private void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/epics")) {
            String epicsJson = gson.toJson(taskManager.getAllEpics());
            sendSuccess(exchange, epicsJson);
        } else if (path.matches("/epics/\\d+")) {
            String[] pathParts = path.split("/");
            int epicId = Integer.parseInt(pathParts[2]);

            Epic epic = taskManager.getEpicById(epicId);
            if (epic != null) {
                String epicJson = gson.toJson(epic);
                sendSuccess(exchange, epicJson);
            } else {
                sendNotFound(exchange);
            }
        } else if (path.matches("/epics/\\d+/subtasks")) {
            String[] pathParts = path.split("/");
            int epicId = Integer.parseInt(pathParts[2]);

            Epic epic = taskManager.getEpicById(epicId);
            if (epic != null) {
                String subtasksJson = gson.toJson(taskManager.getSubtasksByEpicId(epicId));
                sendSuccess(exchange, subtasksJson);
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Optional<Epic> epicOpt = parseJson(exchange, Epic.class);

        if (epicOpt.isEmpty()) {
            sendBadRequest(exchange, "Invalid epic data");
            return;
        }

        Epic epic = epicOpt.get();

        try {
            if (epic.getId() == 0) {
                Epic createdEpic = taskManager.createEpic(epic);
                String epicJson = gson.toJson(createdEpic);
                sendCreated(exchange, epicJson);
            } else {
                taskManager.updateEpic(epic);
                sendCreated(exchange, "{\"message\":\"Epic updated successfully\"}");
            }
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/epics")) {
            taskManager.deleteAllEpics();
            sendSuccess(exchange, "{\"message\":\"All epics deleted\"}");
        } else if (path.matches("/epics/\\d+")) {
            String[] pathParts = path.split("/");
            int epicId = Integer.parseInt(pathParts[2]);

            Epic epic = taskManager.getEpicById(epicId);
            if (epic != null) {
                taskManager.deleteEpic(epicId);
                sendSuccess(exchange, "{\"message\":\"Epic deleted\"}");
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }
}