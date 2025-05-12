package CLI;

import dto.Task;
import dto.TaskStatus;
import infrastructure.Configuration;
import service.TaskManager;

import java.util.UUID;

public class TaskUpdateMenu extends AbstractReplRunner {
    private final TaskManager taskManager;

    public TaskUpdateMenu() {
        super(
                "Главное меню >> Обновление задач",
                new String[]{
                        "Изменить статус задачи",
                        "Изменить реквизиты задачи",
                        "Удалить задачу по uuid",
                        "Очистить список задач"
                }
        );
        taskManager = Configuration.getInstance().getTaskManager();
    }

    @Override
    void processCommand(int command) {
        switch (command) {
            case 1:
                updateStatus();
                break;
            case 2:
                updateTaskData();
                break;
            case 3:
                removeTask();
                break;
            case 4:
                clearTasks();
                break;
        }
    }

    private void clearTasks() {
        taskManager.clearTasks();
    }

    private void removeTask() {
        UUID uuid = queryTaskUuidFromStdin();
        taskManager.removeTask(uuid);
    }

    private void updateTaskData() {
        UUID uuid = queryTaskUuidFromStdin();
        String name = queryStringFromStdin("Введите новое имя");
        String description = queryStringFromStdin("Введите новое описание");

        Task task = new Task(uuid, name, description);

        taskManager.updateTask(task);
    }

    private void updateStatus() {
        UUID uuid = queryTaskUuidFromStdin();
        Task task = taskManager.getTask(uuid);

        int statusNumber = queryIntFromStdin("Введите новый статус [1 - New, 2 - In progress, 3 - Done]", 1, 3);
        task.setStatus(TaskStatus.values()[statusNumber - 1]);
        taskManager.updateTask(task);
    }

    private UUID queryTaskUuidFromStdin() {
        String uuid = queryStringFromStdin("Введите uuid задачи");
        return UUID.fromString(uuid);
    }
}
