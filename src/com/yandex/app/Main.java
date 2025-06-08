import com.yandex.app.service.*;
import com.yandex.app.model.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создаем задачи
        Task task1 = new Task("Помыть посуду", "Помыть посуду после ужина", Progress.NEW);
        manager.createTask(task1);

        // Создаем эпик с подзадачами
        Epic epic1 = new Epic("Переезд", "Организация переезда в новый офис");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Собрать коробки", "Упаковать вещи", Progress.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Нанять грузчиков", "Найти компанию", Progress.NEW, epic1.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        // Получаем задачи, чтобы добавить в историю
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getSubtaskById(subtask2.getId());

        // Выводим историю
        System.out.println("История просмотров:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}