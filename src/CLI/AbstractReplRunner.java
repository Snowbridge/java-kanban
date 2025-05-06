package CLI;

import Entity.Task.Epic;
import Entity.Task.Subtask;
import Entity.Task.Task;

import java.util.*;

/**
 * REPL - Read-Eval-Print-Loop
 * класс, инкапсулирующий общую логику для консольного меню приложения:
 * 1. Вывод меню
 * 2. Запрос команды
 * 3. Вызов обработчиков команд
 * 4. Обработка несуществующих команд и исключений
 */
public abstract class AbstractReplRunner {
    static final int EXIT_COMMAND = 0;
    final ArrayList<String> menu;
    final Scanner scanner = new Scanner(System.in);
    final String menuHeader;

    public AbstractReplRunner(String menuHeader, String[] menu) {
        this.menuHeader = menuHeader;
        this.menu = new ArrayList<>();
        this.menu.add("Выход"); // нулевой элемент - это в любом меню всегда "выход"
        this.menu.addAll(Arrays.asList(menu));
    }

    public void run() {
        while (true) {
            println(menuHeader);
            printMenu();

            try {
                int command = queryIntFromStdin("Введите команду", 0, menu.size() - 1);
                if (command == EXIT_COMMAND) {
                    return;
                }
                if (command > 0 && command < menu.size()) {
                    processCommand(command);
                } else
                    println("Не известная команда");
            } catch (InputMismatchException e) {
                scanner.nextLine();
                println("❗Введены не корректные данные");
            } catch (Exception e) {
                println("❗" + e.getMessage());
            }
        }

    }

    // обработка команд реализуется в конкретных классах-имплементациях
    abstract void processCommand(int command);

    void printMenu() {
        for (int i = 1; i < menu.size(); i++) {
            println(String.format("%d. %s", i, menu.get(i)));
        }
        println("0. Выход");
        println("");
    }

    int queryIntFromStdin(String prompt) {
        print(prompt + ": ");
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }

    int queryIntFromStdin(String prompt, int min, int max) {
        int value = queryIntFromStdin(String.format("%s [%d..%d]", prompt, min, max));
        if (value < min || value > max) {
            throw new IllegalArgumentException("Значение должно быть в диапазоне " + min + ".." + max);
        }
        return value;
    }

    String queryStringFromStdin(String prompt) {
        print(prompt + ": ");
        return scanner.nextLine();
    }

    // Метод слегка больной: он ничего не выведет, если в taskList будут только сабтаски без родителя.
    // Но вывод на консоль в задачу вообще не входит, он нужен только для отладки, по этмоу я забил
    void print(List<Task> taskList) {
        String header = String.format("%s\t| %s\t| %s\t| %s", "UUID", "Status", "Name", "Description");
        println(header);
        println("-".repeat(header.length()));

        String patternTask = "%s | %s %s | %s | %s%n";
        String patternSubtask = "%s | %s %s | ⌊ %s | %s%n";

        // Сначала выведем все свободные таски
        taskList.stream()
                .filter(it -> !(it instanceof Epic || it instanceof Subtask))
                .forEach(it -> print(String.format(patternTask, it.getUuid(), it.getStatus(), it.icon(), it.getName(), it.getDescription())));

        // потом эпики и под эпиками их сабтаски
        taskList.stream()
                .filter(it -> it instanceof Epic)
                .forEach(it -> {
                    print(String.format(patternTask, it.getUuid(), it.getStatus(), it.icon(), it.getName(), it.getDescription()));
                    taskList.stream()
                            .filter(st -> st instanceof Subtask subtask && subtask.getParent() == it)
                            .forEach(subtask -> print(String.format(patternSubtask, subtask.getUuid(), subtask.getStatus(), subtask.icon(), subtask.getName(), subtask.getDescription())));
                });
    }

    void print(String line) {
        System.out.print(line);
    }

    void println(String line) {
        print(line + System.lineSeparator());
    }
}
