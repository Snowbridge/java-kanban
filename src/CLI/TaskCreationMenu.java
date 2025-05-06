package CLI;

import Entity.Task.Epic;
import Entity.Task.Task;
import infrastructure.Configuration;
import service.TaskManager;

import java.util.UUID;

public class TaskCreationMenu extends AbstractReplRunner {
    private final TaskManager taskManager;

    public TaskCreationMenu() {
        super(
                "Главное меню >> Создание задач",
                new String[]{
                        "Создать задачу",
                        "Создать эпик",
                        "Создать подзадачу в эпике"
                }
        );
        taskManager = Configuration.getInstance().getTaskManager();
    }

    @Override
    void processCommand(int command) {
        switch (command) {
            case 1:
                createNewTask();
                break;
            case 2:
                createNewEpic();
                break;
            case 3:
                createNewSubtask();
                break;
        }
    }

    private void createNewSubtask() {
        String uuidString = queryStringFromStdin("Введите uuid эпика");
        UUID epicUuid = UUID.fromString(uuidString);
        Epic epic = (Epic) taskManager.getTask(epicUuid);

        String name = queryStringFromStdin("Ведите название подзадачи");
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название подзадачи не может быть пустым");
        }

        String description = queryStringFromStdin("Введите описание подзадачи");
        Task task = taskManager.addTask(epic, name, description);
        print("Создана подзадача с id " + task.getUuid());
    }

    private void createNewEpic() {
        String name = queryStringFromStdin("Ведите название эпика");
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название эпика не может быть пустым");
        }

        String description = queryStringFromStdin("Введите описание эпика");
        Epic epic = taskManager.addEpic(name, description);
        print("Создан эпик с id " + epic.getUuid());
    }

    private void createNewTask() {
        String name = queryStringFromStdin("Ведите название задачи");
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }

        String description = queryStringFromStdin("Введите описание задачи");
        Task task = taskManager.addTask(name, description);
        print("Создана задача с id " + task.getUuid());
    }
}
