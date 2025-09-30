package com.yandex.app;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.model.Progress;
import com.yandex.app.service.TaskManager;
import com.yandex.app.service.FileBackedTaskManager;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File("tasks.csv");
        TaskManager fileManager = FileBackedTaskManager.loadFromFile(file);

        Task task1 = new Task("Task 1", "Description 1", Progress.NEW);
        Task task2 = new Task("Task 2", "Description 2", Progress.NEW);

        fileManager.addTask(task1);
        fileManager.addTask(task2);

        Epic epic1 = new Epic("Epic 1", "Epic description");
        fileManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Subtask description", Progress.NEW, epic1.getId());
        fileManager.addSubtask(subtask1);

        System.out.println("All tasks:");
        for (Task task : fileManager.getAllTasks()) {
            System.out.println("Task");
        }

        System.out.println("All epics:");
        for (Epic epic : fileManager.getAllEpics()) {
            System.out.println("Epic");
        }

        System.out.println("All subtasks:");
        for (Subtask subtask : fileManager.getAllSubtasks()) {
            System.out.println("Subtask");
        }

        System.out.println("History:");
        for (Task task : fileManager.getHistory()) {
            System.out.println("History item");
        }

        TaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);
        System.out.println("Loaded tasks:");
        for (Task task : loadedManager.getAllTasks()) {
            System.out.println("Loaded task");
        }
    }
}