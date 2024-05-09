package fr.univrouen.tasklistObserver;

/**
 * L'interface TaskListObservable définit les méthodes nécessaires
 * pour ajouter, supprimer et notifier les observateurs d'une liste de tâches.
 */
public interface TaskListObservable {
    /**
     * Ajoute un observateur à la liste des observateurs.
     *
     * @param observer l'observateur à ajouter.
     */
    void addObserver(TaskListObserver observer);

    /**
     * Supprime un observateur de la liste des observateurs.
     *
     * @param observer l'observateur à supprimer.
     */
    void removeObserver(TaskListObserver observer);

    /**
     * Notifie tous les observateurs en appelant leur méthode update.
     */
    void notifyObservers();
}
