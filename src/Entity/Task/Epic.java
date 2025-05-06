package Entity.Task;

public class Epic extends Task {
    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public String icon() {
        return "☑️";
    }
}
