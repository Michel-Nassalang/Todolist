package fr.univrouen.tasklist;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import parser.TaskHandler;
import taskManager.ComplexTask;
import taskManager.TaskComponent;

public class FileTaskListFactory implements TaskListFactory {
    private String fileName;

    public FileTaskListFactory(String fileName) {
        this.fileName = fileName;
    }

    public static List<TaskComponent> loadTaskListFromXML(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true); // Activer la validation par DTD
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            TaskHandler handler = new TaskHandler();
            parser.parse(filePath, handler);
            return handler.getTasks();
        } catch (Exception e) {
            // Gestion des erreurs
            // System.out.println(e);
            return null;
        }
    }

    @Override
    public TaskList createTaskList() {
        TaskList taskList = new TaskList();
        List<TaskComponent> tasklistloaded = loadTaskListFromXML(fileName);
        for (TaskComponent taskComponent : tasklistloaded) {
            System.out.println(taskComponent.getDescription() + " "+taskComponent.getProgress());
            if (taskComponent instanceof ComplexTask) {
                for(TaskComponent tComponent : ((ComplexTask)taskComponent).getSubTasks()){
                    System.out.println("  --subtask - "+ tComponent.getDescription() +tComponent.getDueDate());
                }
            }
        }
        taskList.setAllTasks(tasklistloaded);
        return taskList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

