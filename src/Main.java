import dto.Epic;
import dto.Subtask;
import dto.Task;
import dto.TaskStatus;

import java.util.Random;

public class Main {
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        Task task = taskManager.addTask("Task", "Task description");
        Epic parent = taskManager.addEpic("Epic ", "Epic description");
        for (int i = 0; i < 5; i++) {
            taskManager.addSubtask(parent, "Subtask", "Subtask description " + i+1);
        }

        System.out.println("List of tasks, epics and subtasks:");
        taskManager.getAllTasks().forEach(Main::printTask);
        taskManager.getAllEpics().forEach(Main::printEpic);

        System.out.println();
        System.out.println("Updating task, epic and subtasks");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setDescription("New description");
        taskManager.updateTask(task);

        Subtask subtask = taskManager.getEpicsSubtasks(parent).stream().skip(1).findFirst().orElseThrow();
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        subtask.setDescription("New subtask description");
        taskManager.updateSubtask(subtask);

        System.out.println();
        System.out.println("Listing again. Epic should be in progress");
        taskManager.getAllTasks().forEach(Main::printTask);
        taskManager.getAllEpics().forEach(Main::printEpic);

        System.out.println();
        System.out.println("All subtasks are set to done");
        taskManager.getEpicsSubtasks(parent)
                .forEach(it->{
                    it.setStatus(TaskStatus.DONE);
                    taskManager.updateSubtask(it);
                });
        taskManager.getAllTasks().forEach(Main::printTask);
        taskManager.getAllEpics().forEach(Main::printEpic);

        subtask = taskManager.getEpicsSubtasks(parent).stream().skip(3).findFirst().orElseThrow();
        subtask.setStatus(TaskStatus.NEW);
        taskManager.updateSubtask(subtask);
        System.out.println();
        System.out.println("Epic expected to become in progress again");
        printEpic(parent);
    }

    private static void printTask(Task task) {
        System.out.printf("%s | %d | %s | %s | %s%n", task.getIcon(), task.getId(), task.getStatus(), task.getName(), task.getDescription());
    }

    private static void printEpic(Epic epic) {
        printTask(epic);
        taskManager.getEpicsSubtasks(epic)
                .forEach(subtask -> {
                    System.out.printf("%s | %d | %s | %s | %s%n", subtask.getIcon(), subtask.getId(), subtask.getStatus(), subtask.getName(), subtask.getDescription());
                });
    }
}
