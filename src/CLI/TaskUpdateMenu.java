package CLI;

import Entity.Task.Task;
import Entity.Task.TaskStatus;
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
        Task task = queryTask();
        taskManager.removeTask(task.getUuid());
    }

    private void updateTaskData() {
        Task task = queryTask();
        String name = queryStringFromStdin("Введите новое имя");
        String description = queryStringFromStdin("Введите новое описание");

        if (name != null && !name.isEmpty())
            task.setName(name);
        if (description != null && !description.isEmpty())
            task.setDescription(description);

        taskManager.updateTask(task);
    }

    private void updateStatus() {
        Task task = queryTask();

        int statusNumber = queryIntFromStdin("Введите новый статус [1 - New, 2 - In progress, 3 - Done]", 1, 3);
        task.setStatus(TaskStatus.values()[statusNumber - 1]);
        taskManager.updateTask(task);
    }

    private Task queryTask() {
        String uuid = queryStringFromStdin("Введите uuid задачи");
        return taskManager.getTask(UUID.fromString(uuid));
    }
}
