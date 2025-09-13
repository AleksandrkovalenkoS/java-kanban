package com.yandex.app.service;

import com.yandex.app.model.Task;
import java.util.List;

public interface HistoryManager {
<<<<<<< HEAD
    void add(Task task);
    void remove(int id);
    List<Task> getHistory();
=======
    void add(Task task); // Добавляет задачу в историю
    List<Task> getHistory(); // Возвращает список просмотренных задач
>>>>>>> main
}