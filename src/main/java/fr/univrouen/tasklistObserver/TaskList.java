package fr.univrouen.tasklistObserver;

import java.util.ArrayList;
import java.util.List;

import fr.univrouen.task.TaskComponent;

public class TaskList implements TaskListObservable {
    private List<TaskComponent> tasks = new ArrayList<>();
    private List<TaskListObserver> observers = new ArrayList<>();

    public void addTask(TaskComponent task){
        tasks.add(task);
        this.notifyObservers();
    }

    public void removeTask(TaskComponent task) {
        tasks.remove(task);
        this.notifyObservers();
    }

    public void editTask(TaskComponent dtask, TaskComponent mtask){
        if(dtask != null && mtask != null && tasks.contains(dtask)){
            int index = tasks.indexOf(dtask);
            tasks.set(index, mtask);
            this.notifyObservers();
        }
    }
    public List<TaskComponent> getAllTasks() {
        return tasks;
    }
    public void setAllTasks(List<TaskComponent> tasks){
        this.tasks = tasks;
        this.notifyObservers();
    }

    @Override
    public void  addObserver(TaskListObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TaskListObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (TaskListObserver observer : observers) {
            observer.update(tasks);
        }
    }
}
