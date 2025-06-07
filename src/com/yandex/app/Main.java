import com.yandex.app.service.*;
import com.yandex.app.model.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new InMemoryTaskManager();

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

        // Выводим задачи
        System.out.println("Все задачи:");
        manager.getAllTasks().forEach(System.out::println);

        System.out.println("\nВсе эпики:");
        manager.getAllEpics().forEach(System.out::println);

        System.out.println("\nВсе подзадачи:");
        manager.getAllSubtasks().forEach(System.out::println);

        // Меняем статусы
        subtask1.setStatus(Progress.IN_PROGRESS);
        manager.updateSubtask(subtask1);

        subtask2.setStatus(Progress.DONE);
        manager.updateSubtask(subtask2);

        // Проверяем статус эпика
        System.out.println("\nСтатус эпика после изменения подзадач:");
        System.out.println(manager.getEpicById(epic1.getId()).getStatus());

        // Удаляем задачу
        manager.deleteTask(task1.getId());
        System.out.println("\nЗадачи после удаления:");
        manager.getAllTasks().forEach(System.out::println);
    }
}