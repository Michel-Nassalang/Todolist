package fr.univrouen.analyzer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistFactory.FileTaskListFactory;
import fr.univrouen.tasklistObserver.TaskList;
import fr.univrouen.tasklistFactory.TaskListFactory;
import fr.univrouen.tasklistObserver.TreeViewTask;

/**
 * La classe TaskAnalyzer fournit des fonctionnalités d'analyse pour les listes de tâches.
 * Elle utilise le pattern Singleton pour garantir une seule instance de l'analyseur dans l'application.
 */
public class TaskAnalyzer {

    private static TaskAnalyzer instance;
    private TaskList taskList;

    private TaskAnalyzer() {}

    /**
     * Méthode statique pour obtenir l'instance unique de TaskAnalyzer.
     *
     * @return l'instance unique de TaskAnalyzer.
     */
    public static TaskAnalyzer getInstance() {
        if (instance == null) {
            instance = new TaskAnalyzer();
        }
        return instance;
    }

    /**
     *
     * @return tasklist la liste de tache retournée
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     *
     * @param taskList liste de tache
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Méthode pour charger la liste des tâches à partir d'un fichier XML.
     *
     * @param filename    le nom du fichier XML à charger.
     * @param treeViewTask le TreeViewTask à mettre à jour avec la liste des tâches chargées.
     * @return true si le chargement a réussi, sinon false.
     */
    public boolean loadFromFile(String filename, TreeViewTask treeViewTask){
        TaskListFactory taskListFactory = new FileTaskListFactory(filename);
        setTaskList(taskListFactory.createTaskList());
        taskList.addObserver(treeViewTask);
        taskList.notifyObservers();
        return taskList != null;
    }

    /**
     * Méthode pour charger la liste des tâches à partir d'un fichier XML.
     *
     * @param filename le nom du fichier XML à charger.
     * @return la liste des tâches chargée.
     */
    public TaskList loadFromFile(String filename){
        TaskListFactory taskListFactory = new FileTaskListFactory(filename);
        setTaskList(taskListFactory.createTaskList());
        return taskList;
    }

    /**
     * Méthode pour obtenir les 5 premières tâches incomplètes, triées par date d'échéance.
     *
     * @return une liste des 5 premières tâches incomplètes, triées par date d'échéance.
     */
    public List<TaskComponent> getTop5IncompleteTasks() {
        if(taskList != null && taskList.getAllTasks() != null){
            List<TaskComponent> top5 = taskList.getAllTasks().stream()
                    .filter(t -> t.getProgress() < 1)
                    .map(t -> (TaskComponent) t)
                    .sorted(Comparator.comparing(TaskComponent::getDueDate))
                    .limit(5)
                    .collect(Collectors.toList());
            for (TaskComponent task : top5) {
                System.out.println(task);
            }
            return top5;
        }else {
            return null;
        }
    }

    /**
     * Méthode pour ordonner la liste des tâches par date d'échéance.
     *
     * @return la liste des tâches ordonnée par date d'échéance.
     */
    public List<TaskComponent> orderTaskList() {
        if (taskList != null && taskList.getAllTasks() != null){
            List<TaskComponent> orderTask = taskList.getAllTasks().stream()
                    .map(t -> (TaskComponent) t)
                    .sorted(Comparator.comparing(TaskComponent::getDueDate))
                    .collect(Collectors.toList());
            for (TaskComponent task : orderTask) {
                System.out.println(task);
            }
            return orderTask;
        }else {
            return null;
        }
    }

}
