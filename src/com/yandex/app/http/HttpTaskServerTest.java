package com.yandex.app.http;

import com.yandex.app.model.Task;
import com.yandex.app.model.Progress;
import com.yandex.app.service.InMemoryTaskManager;
import com.yandex.app.service.TaskManager;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private TaskManager manager;
    private HttpTaskServer taskServer;
    private Gson gson;
    private HttpClient client;

    @BeforeEach
    void setUp() throws IOException {
        manager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(manager);
        taskServer.start();
        gson = new Gson();
        client = HttpClient.newHttpClient();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }

    @Test
    void testAddTask() throws IOException, InterruptedException {
        Task task = new Task("Test Task", "Testing task", Progress.NEW,
                Duration.ofMinutes(30), LocalDateTime.now());

        String taskJson = gson.toJson(task);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = manager.getAllTasks();
        assertNotNull(tasksFromManager);
        assertEquals(1, tasksFromManager.size());
        assertEquals("Test Task", tasksFromManager.get(0).getTitle());
    }

    @Test
    void testGetTask() throws IOException, InterruptedException {
        Task task = new Task("Test Task", "Testing task", Progress.NEW);
        Task createdTask = manager.createTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/" + createdTask.getId()))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task responseTask = gson.fromJson(response.body(), Task.class);
        assertNotNull(responseTask);
        assertEquals(createdTask.getId(), responseTask.getId());
        assertEquals("Test Task", responseTask.getTitle());
    }

    @Test
    void testGetAllTasks() throws IOException, InterruptedException {
        manager.createTask(new Task("Task 1", "Description 1", Progress.NEW));
        manager.createTask(new Task("Task 2", "Description 2", Progress.IN_PROGRESS));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task[] tasks = gson.fromJson(response.body(), Task[].class);
        assertNotNull(tasks);
        assertEquals(2, tasks.length);
    }

    @Test
    void testDeleteTask() throws IOException, InterruptedException {
        Task task = new Task("Test Task", "Testing task", Progress.NEW);
        Task createdTask = manager.createTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/" + createdTask.getId()))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    void testGetHistory() throws IOException, InterruptedException {
        Task task = new Task("Test Task", "Testing task", Progress.NEW);
        Task createdTask = manager.createTask(task);
        manager.getTaskById(createdTask.getId());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/history"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task[] history = gson.fromJson(response.body(), Task[].class);
        assertNotNull(history);
        assertEquals(1, history.length);
    }

    @Test
    void testGetPrioritized() throws IOException, InterruptedException {
        Task task = new Task("Test Task", "Testing task", Progress.NEW,
                Duration.ofMinutes(30), LocalDateTime.now());
        manager.createTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/prioritized"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task[] prioritized = gson.fromJson(response.body(), Task[].class);
        assertNotNull(prioritized);
        assertEquals(1, prioritized.length);
    }
}