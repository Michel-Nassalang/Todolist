package fr.univrouen.taskVisitor;

import fr.univrouen.task.*;
import fr.univrouen.task.Priority;
import org.xml.sax.Attributes;

import java.time.LocalDate;

/**
 * La classe TaskAttributePopulator est un visiteur qui peuple les attributs d'une tâche
 * à partir des attributs fournis sous forme d'objet Attributes.
 */
public class TaskAttributePopulator implements TaskVisitor {
    private Attributes attributes;

    /**
     * Constructeur de la classe TaskAttributePopulator.
     *
     * @param attributes les attributs à utiliser pour peupler les tâches.
     */
    public TaskAttributePopulator(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * Méthode pour visiter une SimpleTask et peupler ses attributs avec les valeurs
     * correspondantes extraites des attributs fournis.
     *
     * @param task la SimpleTask à peupler.
     */
    @Override
    public void visit(SimpleTask task) {
        task.setDescription(attributes.getValue("description"));
        task.setDueDate(LocalDate.parse(attributes.getValue("dueDate")));
        task.setPriority(Priority.valueOf(attributes.getValue("priority").toUpperCase()));
        task.setEstimatedDate(Integer.parseInt(attributes.getValue("estimatedDate")));
        task.setProgress(Float.parseFloat(attributes.getValue("progress")));
    }

    /**
     * Méthode pour visiter une ComplexTask et peupler son attribut de priorité avec la valeur
     * correspondante extraite des attributs fournis.
     *
     * @param task la ComplexTask à peupler.
     */
    @Override
    public void visit(ComplexTask task) {
        task.setPriority(Priority.valueOf(attributes.getValue("priority").toUpperCase()));
    }
}