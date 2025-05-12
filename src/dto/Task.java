package dto;

import java.util.Objects;
import java.util.UUID;

public class Task {
    private final UUID uuid;
    private String name;
    private String description;
    private TaskStatus status;
    protected String icon;

    public Task(String name, String description) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.icon = "✅";
    }

    public Task(UUID uuid, String name, String description) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.icon = "✅";
    }

    public String getIcon() {
        return icon;
    }

    public UUID getUuid() {
        return uuid;
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
        return Objects.equals(uuid, task.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
