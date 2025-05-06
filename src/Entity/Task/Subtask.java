package Entity.Task;

public class Subtask extends Task {
    private final Epic parent;

    public Subtask(Epic parent, String name, String description) {
        super(name, description);
        this.parent = parent;
    }

    public Epic getParent() {
        return parent;
    }

    @Override
    public String icon() {
        return "✔️";
    }
}
