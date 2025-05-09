package repository.impl;

import dto.Epic;
import dto.Subtask;
import dto.Task;
import dto.TaskStatus;
import repository.TasksRepository;

import java.util.*;

public class InMemoryTaskRepository implements TasksRepository {

    private final Map<UUID, Task> tasks;

    public InMemoryTaskRepository() {
        tasks = new HashMap<>();
    }

    @Override
    public void addTask(Task task) {
        tasks.put(task.getUuid(), task);
    }

    @Override
    public Task getTask(UUID uuid) {
        return tasks.get(uuid);
    }

    @Override
    public boolean containsTask(Task task) {
        return containsTask(task.getUuid());
    }

    @Override
    public boolean containsTask(UUID uuid) {
        return tasks.containsKey(uuid);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> getTasks(TaskStatus status) {
        return tasks.values()
                .stream()
                .filter(it -> it.getStatus() == status)
                .toList();
    }

    @Override
    public List<Subtask> getSubtasks(UUID epicUuid) {
        Epic epic = (Epic) tasks.get(epicUuid);
        return tasks.values()
                .stream()
                .filter(it -> (it instanceof Subtask) && ((Subtask) it).getParent().equals(epic))
                .map(Subtask.class::cast)
                .toList();
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getUuid(), task);
    }

    @Override
    public void removeTask(UUID uuid) {
        tasks.remove(uuid);
    }

    @Override
    public void clearTasks() {
        tasks.clear();
    }
}
