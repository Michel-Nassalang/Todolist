package fr.univrouen.analyzer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import parser.TaskHandler;
import taskManager.SimpleTask;
import taskManager.TaskComponent;

public class TaskAnalyzer {
    public static List<TaskComponent> loadTasksFromFile(String filePath) {
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

    public static List<SimpleTask> getTop5IncompleteTasks(List<TaskComponent> tasks) {
        return tasks.stream()
                .filter(t -> t instanceof SimpleTask && t.getProgress() < 100)
                .map(t -> (SimpleTask) t)
                .sorted(Comparator.comparing(SimpleTask::getDueDate))
                .limit(5)
                .collect(Collectors.toList());
    }

    public static void printTaskList(List<SimpleTask> tasks) {
        for (SimpleTask task : tasks) {
            System.out.println(task.getDescription() + " - Due: " + task.getDueDate());
        }
    }

    public static void main(String[] args) {

        String filePath = "test.xml";
        List<TaskComponent> tasks = loadTasksFromFile(filePath);
        List<SimpleTask> incompleteTasks = getTop5IncompleteTasks(tasks);
        printTaskList(incompleteTasks);
    }
}
