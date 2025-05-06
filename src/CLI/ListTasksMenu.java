package CLI;

import Entity.Task.Task;
import Entity.Task.TaskStatus;
import infrastructure.Configuration;
import service.TaskManager;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class ListTasksMenu extends AbstractReplRunner {
    private final TaskManager taskManager;

    public ListTasksMenu() {
        super(
                "Главное меню >> Просмотр списка задач",
                new String[]{
                        "Просмотреть все задачи",
                        "Просмотреть задачи по статусу",
                        "Просмотреть подзадачи эпика"
                }
        );
        taskManager = Configuration.getInstance().getTaskManager();
    }

    @Override
    void processCommand(int command) {
        switch (command) {
            case 1:
                listAllTasks();
                break;
            case 2:
                listByStatus();
                break;
            case 3:
                listSubtasksByEpic();
        }
    }

    private void listSubtasksByEpic() {
        String uuidString = queryStringFromStdin("Введите uuid эпика");
        UUID epicUuid = UUID.fromString(uuidString);

        List<Task> tasks = Stream.concat(
                Stream.of(taskManager.getTask(epicUuid)),
                taskManager.getSubtasks(epicUuid)
                        .stream()
                        .map(Task.class::cast)
        ).toList(); // собрать в один список эпик и его сабтаски

        print(tasks);
    }

    private void listByStatus() {
        int statusNumber = queryIntFromStdin("Введите статус задач, которые вас интересуют [1 - New, 2 - In progress, 3 - Done]", 1, 3);
        TaskStatus status = TaskStatus.values()[statusNumber - 1];
        print(taskManager.getTasks(status));
    }

    private void listAllTasks() {
        print(taskManager.getTasks());
    }
}
