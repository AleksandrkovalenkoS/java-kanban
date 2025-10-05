package com.yandex.app.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Subtask;
import com.yandex.app.service.TaskManager;
import java.io.IOException;
import java.util.Optional;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;

    public SubtasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    sendNotFound(exchange);
            }
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/subtasks")) {
            String subtasksJson = gson.toJson(taskManager.getAllSubtasks());
            sendSuccess(exchange, subtasksJson);
        } else if (path.matches("/subtasks/\\d+")) {
            String[] pathParts = path.split("/");
            int subtaskId = Integer.parseInt(pathParts[2]);

            Subtask subtask = taskManager.getSubtaskById(subtaskId);
            if (subtask != null) {
                String subtaskJson = gson.toJson(subtask);
                sendSuccess(exchange, subtaskJson);
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Optional<Subtask> subtaskOpt = parseJson(exchange, Subtask.class);

        if (subtaskOpt.isEmpty()) {
            sendBadRequest(exchange, "Invalid subtask data");
            return;
        }

        Subtask subtask = subtaskOpt.get();

        try {
            if (subtask.getId() == 0) {
                Subtask createdSubtask = taskManager.createSubtask(subtask);
                if (createdSubtask != null) {
                    String subtaskJson = gson.toJson(createdSubtask);
                    sendCreated(exchange, subtaskJson);
                } else {
                    sendNotFound(exchange);
                }
            } else {
                taskManager.updateSubtask(subtask);
                sendCreated(exchange, "{\"message\":\"Subtask updated successfully\"}");
            }
        } catch (IllegalArgumentException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/subtasks")) {
            taskManager.deleteAllSubtasks();
            sendSuccess(exchange, "{\"message\":\"All subtasks deleted\"}");
        } else if (path.matches("/subtasks/\\d+")) {
            String[] pathParts = path.split("/");
            int subtaskId = Integer.parseInt(pathParts[2]);

            Subtask subtask = taskManager.getSubtaskById(subtaskId);
            if (subtask != null) {
                taskManager.deleteSubtask(subtaskId);
                sendSuccess(exchange, "{\"message\":\"Subtask deleted\"}");
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }
}