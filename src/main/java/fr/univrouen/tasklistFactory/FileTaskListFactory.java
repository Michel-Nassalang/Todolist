package fr.univrouen.tasklistFactory;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import fr.univrouen.parser.TaskHandler;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistObserver.TaskList;

/**
 * La classe FileTaskListFactory implémente l'interface TaskListFactory
 * pour créer une instance de TaskList à partir d'un fichier XML.
 */
public class FileTaskListFactory implements TaskListFactory {
    private String fileName;

    /**
     * Constructeur de la classe FileTaskListFactory.
     *
     * @param fileName le nom du fichier XML contenant les données des tâches.
     */
    public FileTaskListFactory(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Charge la liste des tâches à partir d'un fichier XML spécifié.
     *
     * @param filePath le chemin du fichier XML à charger.
     * @return une liste des composants de tâches chargée à partir du fichier XML.
     */
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

    /**
     * Crée et retourne une nouvelle instance de TaskList à partir du fichier XML spécifié.
     *
     * @return une nouvelle instance de TaskList.
     */
    @Override
    public TaskList createTaskList() {
        TaskList taskList = new TaskList();
        List<TaskComponent> tasklistloaded = loadTaskListFromXML(fileName);
        taskList.setAllTasks(tasklistloaded);
        return taskList;
    }

    /**
     * Obtient le nom du fichier associé à cette fabrique.
     *
     * @return le nom du fichier.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Définit le nom du fichier associé à cette fabrique.
     *
     * @param fileName le nom du fichier.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

