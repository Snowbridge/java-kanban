import CLI.MainMenu;
import service.TaskGenerator;

public class Main {
    public static void main(String[] args) {
        TaskGenerator.generate();
        new MainMenu().run();
    }
}
