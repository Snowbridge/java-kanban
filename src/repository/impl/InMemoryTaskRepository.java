package repository.impl;

import Entity.Task.Epic;
import Entity.Task.Subtask;
import Entity.Task.Task;
import Entity.Task.TaskStatus;
import repository.TasksRepository;

import java.util.*;

public class InMemoryTaskRepository implements TasksRepository {

    private final Map<UUID, Task> tasks;

    public InMemoryTaskRepository() {
        tasks = new HashMap<>();
    }

    @Override
    public void addTask(Task task) {
        if (tasks.containsKey(task.getUuid())) {
            throw new IllegalArgumentException("Task already exists");
        }

        if (task instanceof Subtask) {
            UUID parentUuid = ((Subtask) task).getParent().getUuid();
            if (!tasks.containsKey(parentUuid)) {
                throw new IllegalArgumentException("Parent task does not exist");
            }
        }

        tasks.put(task.getUuid(), task);
    }

    @Override
    public Task getTask(UUID uuid) {
        if (!tasks.containsKey(uuid)) {
            throw new IllegalArgumentException("Task does not exist");
        }
        return tasks.get(uuid);
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
        if (!tasks.containsKey(epicUuid)) {
            throw new IllegalArgumentException("Epic does not exist");
        }

        Epic epic = (Epic) tasks.get(epicUuid);
        return tasks.values()
                .stream()
                .filter(it -> (it instanceof Subtask) && ((Subtask) it).getParent().equals(epic))
                .map(Subtask.class::cast)
                .toList();
    }

    @Override
    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getUuid())) {
            throw new IllegalArgumentException("Task does not exist");
        }
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
