package fr.univrouen.analyzer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistFactory.FileTaskListFactory;
import fr.univrouen.tasklistObserver.TaskList;
import fr.univrouen.tasklistFactory.TaskListFactory;
import fr.univrouen.tasklistObserver.TreeViewTask;

public class TaskAnalyzer {

    private static TaskAnalyzer instance;
    private TaskList taskList;

    private TaskAnalyzer() {}

    public static TaskAnalyzer getInstance() {
        if (instance == null) {
            instance = new TaskAnalyzer();
        }
        return instance;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public boolean loadFromFile(String filename, TreeViewTask treeViewTask){
        TaskListFactory taskListFactory = new FileTaskListFactory(filename);
        setTaskList(taskListFactory.createTaskList());
        taskList.addObserver(treeViewTask);
        taskList.notifyObservers();
        return taskList != null;
    }


    public TaskList loadFromFile(String filename){
        TaskListFactory taskListFactory = new FileTaskListFactory(filename);
        setTaskList(taskListFactory.createTaskList());
        return taskList;
    }

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
