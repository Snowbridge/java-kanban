package infrastructure;

import repository.TasksRepository;
import repository.impl.InMemoryTaskRepository;
import service.TaskManager;

public class Configuration {

    private static Configuration instance;
    private final TasksRepository tasksRepository;
    private final TaskManager taskManager;
    private Configuration() {
        tasksRepository = new InMemoryTaskRepository();
        taskManager = new TaskManager(tasksRepository);
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public TasksRepository getTasksRepository() {
        return tasksRepository;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
