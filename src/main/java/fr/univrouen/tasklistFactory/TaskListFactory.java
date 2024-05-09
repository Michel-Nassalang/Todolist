package fr.univrouen.tasklistFactory;

import fr.univrouen.tasklistObserver.TaskList;

/**
 * L'interface TaskListFactory définit une méthode pour créer une instance de TaskList.
 * Les classes implémentant cette interface sont responsables de la création de nouvelles listes de tâches.
 */
public interface TaskListFactory {
    /**
     * Crée et retourne une nouvelle instance de TaskList.
     *
     * @return Une nouvelle instance de TaskList.
     */
    TaskList createTaskList();
}
