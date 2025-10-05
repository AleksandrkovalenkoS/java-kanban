package com.yandex.app.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.app.model.Task;
import com.yandex.app.service.TaskManager;
import java.io.IOException;
import java.util.Optional;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;

    public TasksHandler(TaskManager taskManager) {
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

        if (path.equals("/tasks")) {
            String tasksJson = gson.toJson(taskManager.getAllTasks());
            sendSuccess(exchange, tasksJson);
        } else if (path.matches("/tasks/\\d+")) {
            String[] pathParts = path.split("/");
            int taskId = Integer.parseInt(pathParts[2]);

            Task task = taskManager.getTaskById(taskId);
            if (task != null) {
                String taskJson = gson.toJson(task);
                sendSuccess(exchange, taskJson);
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Optional<Task> taskOpt = parseJson(exchange, Task.class);

        if (taskOpt.isEmpty()) {
            sendBadRequest(exchange, "Invalid task data");
            return;
        }

        Task task = taskOpt.get();

        try {
            if (task.getId() == 0) {
                Task createdTask = taskManager.createTask(task);
                String taskJson = gson.toJson(createdTask);
                sendCreated(exchange, taskJson);
            } else {
                taskManager.updateTask(task);
                sendCreated(exchange, "{\"message\":\"Task updated successfully\"}");
            }
        } catch (IllegalArgumentException e) {
            sendHasInteractions(exchange);
        } catch (Exception e) {
            sendInternalError(exchange);
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/tasks")) {
            taskManager.deleteAllTasks();
            sendSuccess(exchange, "{\"message\":\"All tasks deleted\"}");
        } else if (path.matches("/tasks/\\d+")) {
            String[] pathParts = path.split("/");
            int taskId = Integer.parseInt(pathParts[2]);

            Task task = taskManager.getTaskById(taskId);
            if (task != null) {
                taskManager.deleteTask(taskId);
                sendSuccess(exchange, "{\"message\":\"Task deleted\"}");
            } else {
                sendNotFound(exchange);
            }
        } else {
            sendNotFound(exchange);
        }
    }
}