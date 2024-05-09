package fr.univrouen.tasklistObserver;

import fr.univrouen.task.TaskComponent;

import java.util.List;

/**
 * L'interface TaskListObserver définit la méthode update,
 * utilisée pour notifier les observateurs d'une liste de tâches
 * lorsqu'il y a un changement dans la liste.
 */
public interface TaskListObserver {
    /**
     * Méthode appelée pour informer un observateur des changements
     * survenus dans la liste de tâches.
     *
     * @param tasks la liste de tâches mise à jour.
     */
    void update(List<TaskComponent> tasks);
}
