package fr.univrouen.tasklistObserver;

public interface TaskListObservable {
    void addObserver(TaskListObserver observer);
    void removeObserver(TaskListObserver observer);
    void notifyObservers();
}
