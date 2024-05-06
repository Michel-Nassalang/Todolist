package fr.univrouen.editor;

import fr.univrouen.parser.TaskListXMLGenerator;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistFactory.FileTaskListFactory;
import fr.univrouen.tasklistFactory.NewTaskListFactory;
import fr.univrouen.tasklistObserver.TaskList;
import fr.univrouen.tasklistFactory.TaskListFactory;
import fr.univrouen.tasklistObserver.TreeViewTask;


public class TaskEditor {

    private static TaskEditor instance;
    private TaskList taskList;
    private TaskListFactory taskListFactory;

    private TaskEditor() {}

    public static TaskEditor getInstance() {
        if (instance == null) {
            instance = new TaskEditor();
        }
        return instance;
    }

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

    public boolean addTask(TaskComponent task){
        if(taskList != null){
            taskList.addTask(task);
            return taskList.getAllTasks().contains(task);
        }else {
            return false;
        }
    }

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

    public boolean modifyTask(TaskComponent dtask, TaskComponent mtask){
        if(taskList != null){
            taskList.editTask(dtask, mtask);
            return !taskList.getAllTasks().contains(dtask);
        }else{
            return false;
        }
    }

    public boolean saveToFile(String filename){
        if(taskList != null){
            TaskListXMLGenerator xmlGenerator = new TaskListXMLGenerator();
            return xmlGenerator.generateXML(this.taskList, filename);
        }else{
            return false;
        }
    }

    public boolean loadFromFile(String filename, TreeViewTask treeViewTask){
        taskListFactory = new FileTaskListFactory(filename);
        setTaskList(taskListFactory.createTaskList());
        taskList.addObserver(treeViewTask);
        taskList.notifyObservers();
        return taskList != null;
    }

    public boolean createNewTask(TreeViewTask treeViewTask){
        taskListFactory = new NewTaskListFactory();
        setTaskList(taskListFactory.createTaskList());
        taskList.addObserver(treeViewTask);
        taskList.notifyObservers();
        return true;
    }
}

