package fr.univrouen.editor;

import tasklist.TaskList;

public class TaskEditor {
    private static TaskEditor instance;
    private TaskList taskList;

    private TaskEditor(TaskList taskList) {
        this.taskList = taskList;
    }

    public static TaskEditor getInstance(TaskList taskList) {
        if (instance == null) {
            instance = new TaskEditor(taskList);
        }
        return instance;
    }

    public void editTaskList() {
        // Logique pour l'édition de la liste de tâches
        System.out.println("Editing task list...");
    }

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}
	
}

