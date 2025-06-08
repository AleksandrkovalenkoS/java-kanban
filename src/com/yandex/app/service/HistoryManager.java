package com.yandex.app.service;

import com.yandex.app.model.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task); // Добавляет задачу в историю
    List<Task> getHistory(); // Возвращает список просмотренных задач
}