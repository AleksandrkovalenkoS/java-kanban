package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import java.util.List;

public interface TaskManager {
<<<<<<< HEAD
=======
    // Методы для Task
>>>>>>> main
    List<Task> getAllTasks();
    void deleteAllTasks();
    Task getTaskById(int id);
    Task createTask(Task task);
    void updateTask(Task task);
    void deleteTask(int id);

<<<<<<< HEAD
=======
    // Методы для Epic
>>>>>>> main
    List<Epic> getAllEpics();
    void deleteAllEpics();
    Epic getEpicById(int id);
    Epic createEpic(Epic epic);
    void updateEpic(Epic epic);
    void deleteEpic(int id);

<<<<<<< HEAD
=======
    // Методы для Subtask
>>>>>>> main
    List<Subtask> getAllSubtasks();
    void deleteAllSubtasks();
    Subtask getSubtaskById(int id);
    Subtask createSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);
    void deleteSubtask(int id);

<<<<<<< HEAD
    List<Subtask> getSubtasksByEpicId(int epicId);
=======
    // Дополнительные методы
    List<Subtask> getSubtasksByEpicId(int epicId);

    // Новый метод для истории просмотров
>>>>>>> main
    List<Task> getHistory();
}