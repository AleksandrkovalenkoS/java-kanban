<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
package com.yandex.app;

import com.yandex.app.service.Managers;
import com.yandex.app.service.TaskManager;
import com.yandex.app.model.Task;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Progress;
import java.util.List;
<<<<<<< HEAD
=======
=======
import com.yandex.app.service.*;
import com.yandex.app.model.*;
>>>>>>> main
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

<<<<<<< HEAD
        Task task1 = new Task("Задача 1", "Описание 1", Progress.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", Progress.NEW);
=======
<<<<<<< HEAD
        Task task1 = new Task("Задача 1", "Описание 1", Progress.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", Progress.NEW);
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Progress.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", Progress.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание 3", Progress.NEW, epic1.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.createEpic(epic2);

        System.out.println("=== Запросы задач ===");
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getTaskById(task2.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getTaskById(task1.getId());

        System.out.println("\n=== История после запросов ===");
        printHistory(manager.getHistory());

        System.out.println("\n=== Удаляем задачу 1 ===");
        manager.deleteTask(task1.getId());
        printHistory(manager.getHistory());

        System.out.println("\n=== Удаляем эпик 1 ===");
        manager.deleteEpic(epic1.getId());
        printHistory(manager.getHistory());
    }

    private static void printHistory(List<Task> history) {
        if (history.isEmpty()) {
            System.out.println("История пуста");
            return;
        }

        for (Task task : history) {
            if (task instanceof Epic) {
                System.out.println("Эпик: " + task.getTitle() + " (ID: " + task.getId() + ")");
            } else if (task instanceof Subtask) {
                System.out.println("Подзадача: " + task.getTitle() + " (ID: " + task.getId() + ")");
            } else {
                System.out.println("Задача: " + task.getTitle() + " (ID: " + task.getId() + ")");
            }
=======
        // Создаем задачи
        Task task1 = new Task("Помыть посуду", "Помыть посуду после ужина", Progress.NEW);
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Progress.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", Progress.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание 3", Progress.NEW, epic1.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.createEpic(epic2);

        System.out.println("=== Запросы задач ===");
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getTaskById(task2.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getTaskById(task1.getId());

<<<<<<< HEAD
        System.out.println("\n=== История после запросов ===");
        printHistory(manager.getHistory());

        System.out.println("\n=== Удаляем задачу 1 ===");
        manager.deleteTask(task1.getId());
        printHistory(manager.getHistory());

        System.out.println("\n=== Удаляем эпик 1 ===");
        manager.deleteEpic(epic1.getId());
        printHistory(manager.getHistory());
    }

    private static void printHistory(List<Task> history) {
        if (history.isEmpty()) {
            System.out.println("История пуста");
            return;
        }

        for (Task task : history) {
            if (task instanceof Epic) {
                System.out.println("Эпик: " + task.getTitle() + " (ID: " + task.getId() + ")");
            } else if (task instanceof Subtask) {
                System.out.println("Подзадача: " + task.getTitle() + " (ID: " + task.getId() + ")");
            } else {
                System.out.println("Задача: " + task.getTitle() + " (ID: " + task.getId() + ")");
            }
=======
        // Выводим историю
        System.out.println("История просмотров:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
>>>>>>> main
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
        }
    }
}