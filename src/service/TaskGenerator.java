package service;

import dto.Epic;
import infrastructure.Configuration;

public class TaskGenerator {
    private final static TaskManager manager = Configuration.getInstance().getTaskManager();

    public static void generate() {
        generateTasks();
        generateEpics();
    }

    private static void generateEpics() {
        Epic epic = manager.addEpic("План на день", "Целый из разных активностей");
        manager.addTask(epic, "Составить план на день", "День будет продуктивным, если правильно подготовиться!");
        manager.addTask(epic, "Пообедать", "Планирование - энергоёмкий процесс");
        manager.addTask(epic, "Отдохнуть от проделанной работы", "К этому времени выполнено целых две задачи");

    }

    private static void generateTasks() {
        manager.addTask("Посетить тренинг \"Танец утюга\"", "ул Фывапролджэвская 12, офис 5, вторник 19:00");
        manager.addTask("Побрить кота", "Кот давно напрашивается");
    }

}
