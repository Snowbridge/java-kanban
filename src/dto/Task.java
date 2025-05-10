package dto;

import java.util.Objects;
import java.util.UUID;

public class Task {
    private static int taskIdCounter = 0;
    private static int nextId(){
        return ++taskIdCounter;
    }

    private final int id;
    private String name;
    private String description;
    private TaskStatus status;
    protected String icon;

    public Task(String name, String description) {
        this.id = Task.nextId();
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.icon = "✅";
    }

    public String getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
