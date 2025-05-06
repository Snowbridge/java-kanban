package infrastructure;

import repository.impl.InMemoryTaskRepository;
import service.TaskManager;

public class Configuration {

    private static Configuration instance;
    private final TaskManager taskManager;

    private Configuration() {
        taskManager = new TaskManager(new InMemoryTaskRepository());
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
