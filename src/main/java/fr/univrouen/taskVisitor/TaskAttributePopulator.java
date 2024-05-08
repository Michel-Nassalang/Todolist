package fr.univrouen.taskVisitor;

import fr.univrouen.task.*;
import fr.univrouen.task.Priority;
import org.xml.sax.Attributes;

import java.time.LocalDate;

public class TaskAttributePopulator implements TaskVisitor {
    private Attributes attributes;

    public TaskAttributePopulator(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public void visit(SimpleTask task) {
        task.setDescription(attributes.getValue("description"));
        task.setDueDate(LocalDate.parse(attributes.getValue("dueDate")));
        task.setPriority(Priority.valueOf(attributes.getValue("priority").toUpperCase()));
        task.setEstimatedDate(Integer.parseInt(attributes.getValue("estimatedDate")));
        task.setProgress(Float.parseFloat(attributes.getValue("progress")));
    }

    @Override
    public void visit(ComplexTask task) {
        task.setPriority(Priority.valueOf(attributes.getValue("priority").toUpperCase()));
    }
}