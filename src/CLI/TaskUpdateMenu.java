package CLI;

import Entity.Task.Task;
import Entity.Task.TaskStatus;
import infrastructure.Configuration;
import repository.TasksRepository;

import java.util.UUID;

public class TaskUpdateMenu extends AbstractReplRunner {
    private final TasksRepository tasksRepository;

    public TaskUpdateMenu() {
        super(
                "Главное меню >> Обновление задач",
                new String[]{
                        "Изменить статус задачи",
                        "Изменить реквизиты задачи",
                        "Удалить задачу"
                }
        );
        tasksRepository = Configuration.getInstance().getTasksRepository();
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
        }
    }

    private void removeTask() {
        Task task = queryTask();
        tasksRepository.removeTask(task.getUuid());
    }

    private void updateTaskData() {
        Task task = queryTask();
        String name = queryStringFromStdin("Введите новое имя");
        String description = queryStringFromStdin("Введите новое описание");

        if(name != null && !name.isEmpty())
            task.setName(name);
        if(description != null && !description.isEmpty())
            task.setDescription(description);

        tasksRepository.updateTask(task);
    }

    private void updateStatus() {
        Task task = queryTask();

        int statusNumber = queryIntFromStdin("Введите новый статус [1 - New, 2 - In progress, 3 - Done]", 1, 3);
        task.setStatus(TaskStatus.values()[statusNumber]);
        tasksRepository.updateTask(task);
    }

    private Task queryTask(){
        String uuid = queryStringFromStdin("Введите uuid задачи");
        return tasksRepository.getTask(UUID.fromString(uuid));
    }
}
