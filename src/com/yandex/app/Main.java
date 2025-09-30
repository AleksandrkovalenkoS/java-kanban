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

        Task task1 = new Task("Task 1", "Description 1");
        task1.setStatus(Progress.IN_PROGRESS);
        Task task2 = new Task("Task 2", "Description 2");

        fileManager.addTask(task1);
        fileManager.addTask(task2);

        Epic epic1 = new Epic("Epic 1", "Epic description");
        fileManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Subtask description", epic1.getId());
        fileManager.addSubtask(subtask1);

        System.out.println("Все задачи:");
        for (Task task : fileManager.getAllTasks()) {
            System.out.println("Task: " + task.getName() + " | Status: " + task.getStatus());
        }

        System.out.println("\nВсе эпики:");
        for (Epic epic : fileManager.getAllEpics()) {
            System.out.println("Epic: " + epic.getName() + " | Status: " + epic.getStatus());
        }

        System.out.println("\nВсе подзадачи:");
        for (Subtask subtask : fileManager.getAllSubtasks()) {
            System.out.println("Subtask: " + subtask.getName() + " | Status: " + subtask.getStatus());
        }

        System.out.println("\nИстория:");
        for (Task task : fileManager.getHistory()) {
            System.out.println("History: " + task.getName());
        }

        TaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);
        System.out.println("\nЗагруженные задачи:");
        for (Task task : loadedManager.getAllTasks()) {
            System.out.println("Loaded Task: " + task.getName());
        }
    }
}