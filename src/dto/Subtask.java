package dto;

public class Subtask extends Task {
    private final Epic parent;

    public Subtask(Epic parent, String name, String description) {
        super(name, description);
        this.parent = parent;
        this.icon = "✔️";
    }

    public Epic getParent() {
        return parent;
    }

}
