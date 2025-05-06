package CLI;

public class MainMenu extends AbstractReplRunner {
    public MainMenu() {
        super("Главное меню",
                new String[]{
                        "Просмотр задач",
                        "Создание задач",
                        "Обновление задач",
                        "Удаление задач",
                });
    }

    @Override
    void processCommand(int command) {
        switch (command) {
            case 1:
                new ListTasksMenu().run();
                break;
            case 2:
            case 3:
            case 4:
                throw new RuntimeException("Not implemented yet");
        }
    }
}
