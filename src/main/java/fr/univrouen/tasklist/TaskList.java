package fr.univrouen.tasklist;

import java.util.ArrayList;
import java.util.List;

import taskManager.TaskComponent;

public class TaskList {
    private List<TaskComponent> tasks = new ArrayList<TaskComponent>();
    
    public void addTask(TaskComponent task){
        tasks.add(task);
    }
    public void removeTask(TaskComponent task) {
        tasks.remove(task);
    }
    public void editTask(){
        // modifier une tache
    }
    public List<TaskComponent> getAllTasks() {
        return tasks;
    }
    public void setAllTasks(List<TaskComponent> tasks){
        this.tasks = tasks;
    }
}
