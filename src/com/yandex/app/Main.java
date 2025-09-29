package com.yandex.app;

import com.yandex.app.service.FileBackedTaskManager;
import com.yandex.app.service.Managers;
import com.yandex.app.service.TaskManager;
import com.yandex.app.model.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        File file = new File("tasks.csv");
        TaskManager fileManager = Managers.getFileBackedTaskManager(file);

        Task task1 = new Task("Задача 1", "Описание 1", Progress.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", Progress.IN_PROGRESS);
        fileManager.createTask(task1);
        fileManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        fileManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Progress.DONE, epic1.getId());
        fileManager.createSubtask(subtask1);

        TaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);

        System.out.println("Задачи после загрузки:");
        System.out.println(loadedManager.getAllTasks());
        System.out.println(loadedManager.getAllEpics());
        System.out.println(loadedManager.getAllSubtasks());
    }
}