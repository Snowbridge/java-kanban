import dto.Epic;
import dto.Subtask;
import dto.Task;
import dto.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public Task addTask(String name, String description) {
        Task task = new Task(name, description);
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addEpic(String name, String description) {
        Epic epic = new Epic(name, description);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask addSubtask(Epic parent, String name, String description) {
        Subtask subtask = new Subtask(parent, name, description);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    public List<Subtask> getEpicsSubtasks(Epic parent) {
        return subtasks.values()
                .stream()
                .filter(it -> Objects.equals(it.getParent(), parent))
                .toList();
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        Epic parent = subtask.getParent();

        List<Subtask> siblings = getEpicsSubtasks(parent);

        boolean allDone = siblings.stream().allMatch(it -> it.getStatus() == TaskStatus.DONE);
        boolean allNew = siblings.stream().allMatch(it -> it.getStatus() == TaskStatus.NEW);
        if (allDone) {
            parent.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            parent.setStatus(TaskStatus.NEW);
        } else {
            parent.setStatus(TaskStatus.IN_PROGRESS);
        }
        updateEpic(parent);
    }

    public void removeTask(Task task) {
        tasks.remove(task.getId());
    }

    public void removeEpic(Epic epic) {
        getEpicsSubtasks(epic).forEach(subtask->subtasks.remove(subtask.getId()));
        epics.remove(epic.getId());
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
}
