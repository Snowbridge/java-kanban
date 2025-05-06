package service;

import Entity.Task.Epic;
import Entity.Task.Subtask;
import Entity.Task.Task;
import Entity.Task.TaskStatus;
import repository.TasksRepository;

import java.util.List;
import java.util.UUID;

public class TaskManager {
    private final TasksRepository tasksRepository;

    public TaskManager(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public Task addTask(String name, String description) {
        Task task = new Task(name, description);
        tasksRepository.addTask(task);
        return task;
    }

    public Epic addEpic(String name, String description) {
        Epic epic = new Epic(name, description);
        tasksRepository.addTask(epic);
        return epic;
    }

    public Subtask addTask(Epic parent, String name, String description) {
        Subtask subtask = new Subtask(parent, name, description);
        tasksRepository.addTask(subtask);
        return subtask;
    }

    public Task getTask(UUID uuid) {
        return tasksRepository.getTask(uuid);
    }

    public List<Task> getTasks() {
        return tasksRepository.getTasks();
    }

    public List<Task> getTasks(TaskStatus status) {
        return tasksRepository.getTasks(status);
    }

    public List<Subtask> getSubtasks(UUID epicUuid) {
        return tasksRepository.getSubtasks(epicUuid);
    }

    public void updateTask(Task task) {
        if (task instanceof Subtask subtask) {
            Epic parent = (Epic) tasksRepository.getTask(subtask.getParent().getUuid());
            List<Subtask> subtasks = tasksRepository.getSubtasks(parent.getUuid());

            boolean allSubtasksDone = subtasks.stream()
                    .allMatch(it -> it.getStatus() == TaskStatus.DONE);

            if (allSubtasksDone) {
                parent.setStatus(TaskStatus.DONE);
                tasksRepository.updateTask(parent);
            } else {
                boolean anyInProgress = subtasks.stream()
                        .anyMatch(it -> it.getStatus() == TaskStatus.IM_PROGRESS);
                if (anyInProgress) {
                    parent.setStatus(TaskStatus.IM_PROGRESS);
                    tasksRepository.updateTask(parent);
                }
            }
        }
        tasksRepository.updateTask(task);
    }

    public void removeTask(UUID uuid) {
        Task task = tasksRepository.getTask(uuid);
        tasksRepository.removeTask(uuid);

        // если удалили эпик, то надо и сабтаски грохнуть
        if (task instanceof Epic) {
            tasksRepository.getSubtasks(uuid)
                    .forEach(it -> tasksRepository.removeTask(it.getUuid()));
        }
    }

    public void clearTasks() {
        tasksRepository.clearTasks();
    }
}
