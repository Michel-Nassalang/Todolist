package fr.univrouen.taskVisitor;

import fr.univrouen.parser.TaskListXMLGenerator;
import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.SimpleTask;
import fr.univrouen.task.TaskComponent;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class TaskAttributeWriter implements TaskVisitor {
    private final TaskListXMLGenerator.XMLHandler handler;

    public TaskAttributeWriter(TaskListXMLGenerator.XMLHandler handler) {
        this.handler = handler;
    }

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
            handler.endElement("", "simpleTask", "simpleTask");
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

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
