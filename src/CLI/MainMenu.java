package CLI;

public class MainMenu extends AbstractReplRunner {
    public MainMenu() {
        super("Главное меню",
                new String[]{
                        "Просмотр задач",
                        "Создание задач",
                        "Обновление/удаление задач"
                });
    }

    @Override
    void processCommand(int command) {
        switch (command) {
            case 1:
                new ListTasksMenu().run();
                break;
            case 2:
                new TaskCreationMenu().run();
                break;
            case 3:
                new TaskUpdateMenu().run();
                break;
        }
    }
}
