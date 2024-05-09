package fr.univrouen.tasklistObserver;

import java.util.ArrayList;
import java.util.List;

import fr.univrouen.task.TaskComponent;

/**
 * La classe TaskList implémente l'interface TaskListObservable
 * et représente une liste de tâches observables.
 */
public class TaskList implements TaskListObservable {
    private List<TaskComponent> tasks = new ArrayList<>();
    private List<TaskListObserver> observers = new ArrayList<>();

    /**
     * Ajoute une tâche à la liste et notifie les observateurs.
     *
     * @param task la tâche à ajouter à la liste.
     */
    public void addTask(TaskComponent task){
        tasks.add(task);
        this.notifyObservers();
    }

    /**
     * Supprime une tâche de la liste et notifie les observateurs.
     *
     * @param task la tâche à supprimer de la liste.
     */
    public void removeTask(TaskComponent task) {
        tasks.remove(task);
        this.notifyObservers();
    }

    /**
     * Modifie une tâche dans la liste et notifie les observateurs.
     *
     * @param dtask la tâche à remplacer.
     * @param mtask la nouvelle tâche.
     */
    public void editTask(TaskComponent dtask, TaskComponent mtask){
        if(dtask != null && mtask != null && tasks.contains(dtask)){
            int index = tasks.indexOf(dtask);
            tasks.set(index, mtask);
            this.notifyObservers();
        }
    }
    /**
     * Récupère toutes les tâches de la liste.
     *
     * @return la liste de toutes les tâches.
     */
    public List<TaskComponent> getAllTasks() {
        return tasks;
    }
    /**
     * Définit la liste de toutes les tâches.
     *
     * @param tasks la nouvelle liste de tâches.
     */
    public void setAllTasks(List<TaskComponent> tasks){
        this.tasks = tasks;
        this.notifyObservers();
    }

    /**
     * Ajoute un observateur à la liste des observateurs.
     *
     * @param observer l'observateur à ajouter.
     */
    @Override
    public void  addObserver(TaskListObserver observer) {
        observers.add(observer);
    }

    /**
     * Supprime un observateur de la liste des observateurs.
     *
     * @param observer l'observateur à supprimer.
     */
    @Override
    public void removeObserver(TaskListObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie tous les observateurs en appelant leur méthode update.
     */
    @Override
    public void notifyObservers() {
        for (TaskListObserver observer : observers) {
            observer.update(tasks);
        }
    }
}
