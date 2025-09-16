package com.yandex.app.model;
<<<<<<< HEAD

=======
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
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
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
<<<<<<< HEAD
        if (!subtaskIds.contains(subtaskId)) {
            subtaskIds.add(subtaskId);
        }
=======
        subtaskIds.add(subtaskId);
>>>>>>> main
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId);
    }

<<<<<<< HEAD
    public void clearSubtaskIds() {
=======
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
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
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
<<<<<<< HEAD
}
=======
}
>>>>>>> main
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
