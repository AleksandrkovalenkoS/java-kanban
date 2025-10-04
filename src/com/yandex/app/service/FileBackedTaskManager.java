package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import com.yandex.app.model.Progress;
import java.io.*;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.load();
        return manager;
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,duration,startTime,epic\n");

            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения в файл", e);
        }
    }

    private void load() {
        if (!file.exists()) {
            return;
        }

        try {
            String content = Files.readString(file.toPath());
            String[] lines = content.split("\n");

            for (int i = 1; i < lines.length; i++) {
                Task task = fromString(lines[i]);
                if (task != null) {
                    addTaskToManager(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла", e);
        }
    }

    private String toString(Task task) {
        String durationStr = task.getDuration() != null ?
                String.valueOf(task.getDuration().toMinutes()) : "";
        String startTimeStr = task.getStartTime() != null ?
                task.getStartTime().toString() : "";

        if (task instanceof Epic) {
            return String.format("%d,EPIC,%s,%s,%s,%s,%s,",
                    task.getId(), task.getTitle(), task.getStatus(),
                    task.getDescription(), durationStr, startTimeStr);
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,SUBTASK,%s,%s,%s,%s,%s,%d",
                    subtask.getId(), subtask.getTitle(), subtask.getStatus(),
                    subtask.getDescription(), durationStr, startTimeStr,
                    subtask.getEpicId());
        } else {
            return String.format("%d,TASK,%s,%s,%s,%s,%s,",
                    task.getId(), task.getTitle(), task.getStatus(),
                    task.getDescription(), durationStr, startTimeStr);
        }
    }

    private Task fromString(String value) {
        String[] fields = value.split(",");
        if (fields.length < 5) return null;

        int id = Integer.parseInt(fields[0]);
        String type = fields[1];
        String name = fields[2];
        Progress status = Progress.valueOf(fields[3]);
        String description = fields[4];

        Duration duration = fields.length > 5 && !fields[5].isEmpty() ?
                Duration.ofMinutes(Long.parseLong(fields[5])) : null;
        LocalDateTime startTime = fields.length > 6 && !fields[6].isEmpty() ?
                LocalDateTime.parse(fields[6]) : null;

        switch (type) {
            case "TASK":
                Task task = new Task(name, description, status, duration, startTime);
                task.setId(id);
                return task;
            case "EPIC":
                Epic epic = new Epic(name, description);
                epic.setId(id);
                epic.setStatus(status);
                return epic;
            case "SUBTASK":
                if (fields.length < 8) return null;
                int epicId = Integer.parseInt(fields[7]);
                Subtask subtask = new Subtask(name, description, status, epicId, duration, startTime);
                subtask.setId(id);
                return subtask;
            default:
                return null;
        }
    }

    private void addTaskToManager(Task task) {
        if (task instanceof Epic) {
            epics.put(task.getId(), (Epic) task);
        } else if (task instanceof Subtask) {
            subtasks.put(task.getId(), (Subtask) task);
            Epic epic = epics.get(((Subtask) task).getEpicId());
            if (epic != null) {
                epic.addSubtaskId(task.getId());
            }
        } else {
            tasks.put(task.getId(), task);
        }

        if (task.getId() >= nextId) {
            nextId = task.getId() + 1;
        }
    }

    @Override
    public Task createTask(Task task) {
        Task created = super.createTask(task);
        save();
        return created;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic created = super.createEpic(epic);
        save();
        return created;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Subtask created = super.createSubtask(subtask);
        save();
        return created;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }
}