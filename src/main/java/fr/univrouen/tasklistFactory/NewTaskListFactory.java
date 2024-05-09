package fr.univrouen.tasklistFactory;

import fr.univrouen.tasklistObserver.TaskList;

/**
 * La classe NewTaskListFactory implémente l'interface TaskListFactory
 * pour créer une nouvelle instance de TaskList.
 */
public class NewTaskListFactory implements TaskListFactory {
    /**
     * Crée et retourne une nouvelle instance de TaskList.
     *
     * @return Une nouvelle instance de TaskList.
     */
    @Override
        public TaskList createTaskList() {
            return new TaskList();
        }
}
