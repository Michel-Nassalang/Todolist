package fr.univrouen.tasklistObserver;

import fr.univrouen.task.TaskComponent;

import java.util.List;

public interface TaskListObserver {
    void update(List<TaskComponent> tasks);
}
