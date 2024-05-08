package fr.univrouen.tasklistFactory;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import fr.univrouen.parser.TaskHandler;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistObserver.TaskList;

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
             System.out.println(e);
            return null;
        }
    }

    @Override
    public TaskList createTaskList() {
        TaskList taskList = new TaskList();
        List<TaskComponent> tasklistloaded = loadTaskListFromXML(fileName);
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

