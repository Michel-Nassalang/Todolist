package fr.univrouen.taskVisitor;

import fr.univrouen.parser.TaskListXMLGenerator;
import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.SimpleTask;
import fr.univrouen.task.TaskComponent;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * La classe TaskAttributeWriter est un visiteur chargé d'écrire les attributs d'une tâche
 * dans un gestionnaire XMLHandler, pour la génération d'un fichier XML.
 */
public class TaskAttributeWriter implements TaskVisitor {
    private final TaskListXMLGenerator.XMLHandler handler;

    /**
     * Constructeur de la classe TaskAttributeWriter.
     *
     * @param handler le gestionnaire XMLHandler où écrire les attributs.
     */
    public TaskAttributeWriter(TaskListXMLGenerator.XMLHandler handler) {
        this.handler = handler;
    }

    /**
     * Méthode pour visiter une SimpleTask et écrire ses attributs dans le XMLHandler.
     *
     * @param task la SimpleTask à écrire.
     */
    @Override
    public void visit(SimpleTask task) {
        AttributesImpl attributes = new AttributesImpl();
        attributes.addAttribute("", "description", "description", "CDATA", task.getDescription());
        attributes.addAttribute("", "dueDate", "dueDate", "CDATA", task.getDueDate().toString());
        attributes.addAttribute("", "priority", "priority", "CDATA", task.getPriority().toString());
        attributes.addAttribute("", "estimatedDate", "estimatedDate", "CDATA", String.valueOf(task.getEstimatedDate()));
        attributes.addAttribute("", "progress", "progress", "CDATA", String.valueOf(task.getProgress()));

        try {
            handler.startElement("", "simpleTask", "simpleTask", attributes);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode pour visiter une ComplexTask et écrire ses attributs dans le XMLHandler.
     *
     * @param task la ComplexTask à écrire.
     */
    @Override
    public void visit(ComplexTask task) {
        AttributesImpl attributes = new AttributesImpl();
        attributes.addAttribute("", "priority", "priority", "CDATA", task.getPriority().toString());

        try {
            handler.startElement("", "complexTask", "complexTask", attributes);
            for (TaskComponent subTask : task.getSubTasks()) {
                subTask.accept(this); // Appel récursif pour gérer les sous-tâches
            }
            handler.endElement("", "complexTask", "complexTask");
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }


}
