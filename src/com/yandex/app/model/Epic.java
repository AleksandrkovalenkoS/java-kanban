package com.yandex.app.model;
<<<<<<< HEAD

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds;

    public Epic(String title, String description) {
        super(title, description, Progress.NEW);
        this.subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    public void addSubtaskId(int subtaskId) {
        if (!subtaskIds.contains(subtaskId)) {
            subtaskIds.add(subtaskId);
        }
=======
import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> subtaskIds;

    public Epic(String title, String description){
        super(title,description, Progress.NEW);
        this.subtaskIds=new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
>>>>>>> main
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId);
    }

<<<<<<< HEAD
    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    public void updateStatus(List<Subtask> subtasksList) {
        if (subtasksList == null || subtasksList.isEmpty()) {
            this.setStatus(Progress.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : subtasksList) {
            if (subtask.getStatus() != Progress.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != Progress.DONE) {
                allDone = false;
            }
            if (!allNew && !allDone) {
                break;
            }
        }

        if (allDone) {
            this.setStatus(Progress.DONE);
        } else if (allNew) {
            this.setStatus(Progress.NEW);
        } else {
            this.setStatus(Progress.IN_PROGRESS);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
=======
    public void clearSubtaskIds(){
        subtaskIds.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
>>>>>>> main
