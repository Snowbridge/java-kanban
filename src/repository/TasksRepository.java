package repository;

import Entity.Task.Subtask;
import Entity.Task.Task;
import Entity.Task.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TasksRepository {
    void addTask(Task task);

    Task getTask(UUID uuid);

    List<Task> getTasks();

    List<Task> getTasks(TaskStatus status);

    List<Subtask> getSubtasks(UUID epicUuid);

    void updateTask(Task task);

    void removeTask(UUID uuid);

    void clearTasks();
}
