package Entity.Task;

public enum TaskStatus {
    NEW,
    IM_PROGRESS,
    DONE;

    @Override
    public String toString() {
        String name = name().replace('_', ' ').toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
