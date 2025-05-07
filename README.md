# java-kanban

Приложение разбито на следующие слои:

+ [repository](src%2Frepository) - низкоуровневая работа с хранимыми данными
+ [Entity](src%2FEntity) - сущности, извлекаемые из репозитория
+ [service](src%2Fservice) - бизнес-логика над данными
+ [CLI](src%2FCLI) - интерфейс командной строки
+ [infrastructure](src%2Finfrastructure) - управление работой приложения

## Repository

Пакет содержит интерфейс хранилища и простую реализацию. В дальнейшем добавится реализация для работы с БД.
Репозиторий осуществляет простые операции чтения/записи/удаления без какой-либо дополнительной бизнес логики.
Исключительно работа с самими данными.

+ [TasksRepository.java](src%2Frepository%2FTasksRepository.java) - общий интерфейс, позволяющий в дальнейшем расширять
реализации репозитория
+ [InMemoryTaskRepository.java](src%2Frepository%2Fimpl%2FInMemoryTaskRepository.java) - простая реализация, которая хранит всё в HashMap

## Entity

Сущности, с которыми работает приложение.

+ [Task.java](src%2FEntity%2FTask%2FTask.java) - родитель для всех прочих тасок, содержит всю общую логику и интерфейс
+ [Epic.java](src%2FEntity%2FTask%2FEpic.java) - групповая таска
+ [Subtask.java](src%2FEntity%2FTask%2FSubtask.java) - подзадача эпика. В отличие от остальных имеет дополнительный параметр конструктора и атрибут parent. 
+ [TaskStatus.java](src%2FEntity%2FTask%2FTaskStatus.java) - статусы тасок

## Service

Пакет с сервисами, реализующими бизнес логику приложения.

+ [TaskManager.java](src%2Fservice%2FTaskManager.java) - основной работяга приложения. Интерфейс очень похож на интерфейс репозитория, но отличия в том,
что внутри выполняются различные дополнительные действия и проверки перед вызовом репозитория.  
+ [TaskGenerator.java](src%2Fservice%2FTaskGenerator.java) - создает тестовые данные для облегчения демо, тестирования и отладки.

## CLI

Пакет CLI реализует командный интерфейс со следующим двухуровневым меню.

```bash
Просмотр задач
├── Просмотреть все задачи
├── Просмотреть задачи по статусу
└── Просмотреть подзадачи эпика
Создание задач
├── Создать задачу
├── Создать эпик
└── Создать подзадачу в эпике
Обновление/удаление задач
├── Изменить статус задачи
├── Изменить реквизиты задачи
├── Удалить задачу по uuid
└── Очистить список задач
```

+ [AbstractReplRunner.java](src%2FCLI%2FAbstractReplRunner.java) - общий функционал для работы с консолью
+ [MainMenu.java](src%2FCLI%2FMainMenu.java) - меню верхнего уровня (главное)
+ [ListTasksMenu.java](src%2FCLI%2FListTasksMenu.java) - меню операции чтения списка задач
+ [TaskCreationMenu.java](src%2FCLI%2FTaskCreationMenu.java) - меню операций добавления задач
+ [TaskUpdateMenu.java](src%2FCLI%2FTaskUpdateMenu.java) - меню операций редактирования и удаления задач

## Infrastructure

Деревенский DI/IoC и Application context, обеспечивает существования единственных экземпляров компонентов
жизненного цикла приложения.

[Configuration.java](src%2Finfrastructure%2FConfiguration.java) - синглтон, через который осуществляется доступ к
экземплярам сервисов и репозитория. 