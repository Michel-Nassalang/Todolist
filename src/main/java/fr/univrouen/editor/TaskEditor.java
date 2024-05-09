package fr.univrouen.editor;

import fr.univrouen.parser.TaskListXMLGenerator;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistFactory.FileTaskListFactory;
import fr.univrouen.tasklistFactory.NewTaskListFactory;
import fr.univrouen.tasklistObserver.TaskList;
import fr.univrouen.tasklistFactory.TaskListFactory;
import fr.univrouen.tasklistObserver.TreeViewTask;

/**
 * La classe TaskEditor est responsable de la gestion des opérations d'édition de tâches,
 * telles que l'ajout, la suppression, la modification, la sauvegarde et le chargement à partir d'un fichier.
 * Elle utilise le pattern Singleton pour garantir une seule instance de l'éditeur dans l'application.
 */
public class TaskEditor {

    private static TaskEditor instance;
    private TaskList taskList;
    private TaskListFactory taskListFactory;

    private TaskEditor() {}

    /**
     * Méthode statique pour obtenir l'instance unique de TaskEditor.
     *
     * @return l'instance unique de TaskEditor.
     */
    public static TaskEditor getInstance() {
        if (instance == null) {
            instance = new TaskEditor();
        }
        return instance;
    }

    /**
     *
     * @return tasklist la liste de tache
     */
	public TaskList getTaskList() {
		return taskList;
	}

    /**
     *
     * @param taskList liste de tache à donner
     */
	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

    /**
     * Méthode pour ajouter une tâche à la liste des tâches.
     *
     * @param task la tâche à ajouter.
     * @return true si la tâche a été ajoutée avec succès, sinon false.
     */
    public boolean addTask(TaskComponent task){
        if(taskList != null){
            taskList.addTask(task);
            return taskList.getAllTasks().contains(task);
        }else {
            return false;
        }
    }

    /**
     * Méthode pour supprimer une tâche de la liste des tâches.
     *
     * @param task la tâche à supprimer.
     * @return true si la tâche a été supprimée avec succès, sinon false.
     */
    public boolean removeTask(TaskComponent task){
        if(taskList != null){
            taskList.removeTask(task);
            for (TaskComponent taski : taskList.getAllTasks()) {
                System.out.println(taski.getDescription());
            }
            return !taskList.getAllTasks().contains(task);
        }
        return false;
    }

    /**
     * Méthode pour modifier une tâche dans la liste des tâches.
     *
     * @param dtask la tâche à modifier.
     * @param mtask la nouvelle tâche.
     * @return true si la tâche a été modifiée avec succès, sinon false.
     */
    public boolean modifyTask(TaskComponent dtask, TaskComponent mtask){
        if(taskList != null){
            taskList.editTask(dtask, mtask);
            return !taskList.getAllTasks().contains(dtask);
        }else{
            return false;
        }
    }

    /**
     * Méthode pour sauvegarder la liste des tâches dans un fichier XML.
     *
     * @param filename le nom du fichier XML de sortie.
     * @return true si la sauvegarde a réussi, sinon false.
     */
    public boolean saveToFile(String filename){
        if(taskList != null){
            TaskListXMLGenerator xmlGenerator = new TaskListXMLGenerator();
            return xmlGenerator.generateXML(this.taskList, filename);
        }else{
            return false;
        }
    }

    /**
     * Méthode pour charger la liste des tâches à partir d'un fichier XML.
     *
     * @param filename    le nom du fichier XML à charger.
     * @param treeViewTask le TreeViewTask à mettre à jour avec la liste des tâches chargées.
     * @return true si le chargement a réussi, sinon false.
     */
    public boolean loadFromFile(String filename, TreeViewTask treeViewTask){
        taskListFactory = new FileTaskListFactory(filename);
        setTaskList(taskListFactory.createTaskList());
        taskList.addObserver(treeViewTask);
        taskList.notifyObservers();
        return taskList != null;
    }

    /**
     * Méthode pour créer une nouvelle liste de tâches.
     *
     * @param treeViewTask le TreeViewTask à mettre à jour avec la nouvelle liste de tâches.
     * @return true si la création de la nouvelle liste a réussi, sinon false.
     */
    public boolean createNewTask(TreeViewTask treeViewTask){
        taskListFactory = new NewTaskListFactory();
        setTaskList(taskListFactory.createTaskList());
        taskList.addObserver(treeViewTask);
        taskList.notifyObservers();
        return true;
    }
}

