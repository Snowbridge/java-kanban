package repository;

import dto.Subtask;
import dto.Task;
import dto.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TasksRepository {
    void addTask(Task task);

    Task getTask(UUID uuid);

    boolean containsTask(Task task);

    boolean containsTask(UUID uuid);

    List<Task> getTasks();

    List<Task> getTasks(TaskStatus status);

    List<Subtask> getSubtasks(UUID epicUuid);

    void updateTask(Task task);

    void removeTask(UUID uuid);

    void clearTasks();
}
