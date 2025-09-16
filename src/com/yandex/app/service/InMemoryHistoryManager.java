package com.yandex.app.service;

import com.yandex.app.model.Task;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
=======
<<<<<<< HEAD
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
            this.prev = null;
            this.next = null;
        }
    }

    private final Map<Integer, Node> historyMap = new HashMap<>();
    private Node head;
    private Node tail;
=======
import java.util.LinkedList;
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
<<<<<<< HEAD

    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
            this.prev = null;
            this.next = null;
        }
    }

    private final Map<Integer, Node> historyMap = new HashMap<>();
    private Node head;
    private Node tail;
=======
    private static final int MAX_HISTORY_SIZE = 10;
    private final LinkedList<Task> history = new LinkedList<>();
>>>>>>> main
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
        int taskId = task.getId();
        remove(taskId);

        Node newNode = new Node(task);
        linkLast(newNode);
        historyMap.put(taskId, newNode);
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = historyMap.get(id);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
            historyMap.remove(id);
<<<<<<< HEAD
        }
=======
        }
=======
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeFirst(); // Удаляем самый старый элемент
        }
        history.addLast(task); // Добавляем в конец
>>>>>>> main
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
    }

    @Override
    public List<Task> getHistory() {
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
        return getTasks();
    }

    private void linkLast(Node newNode) {
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }

    private void removeNode(Node nodeToRemove) {
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
        } else {
            head = nodeToRemove.next;
        }

        if (nodeToRemove.next != null) {
            nodeToRemove.next.prev = nodeToRemove.prev;
        } else {
            tail = nodeToRemove.prev;
        }
<<<<<<< HEAD
=======
=======
        return new ArrayList<>(history); // Возвращаем копию для защиты от изменений
>>>>>>> main
>>>>>>> 7dfca1849bb20ce7a99303602542284f32e595b0
    }
}