package fr.univrouen.taskVisitor;

import fr.univrouen.task.*;

/**
 * L'interface TaskVisitor définit les méthodes nécessaires pour visiter les différentes
 * implémentations de la classe TaskComponent, telles que SimpleTask et ComplexTask.
 * Elle est utilisée en conjonction avec le pattern Visitor pour permettre aux objets TaskComponent
 * d'accepter la visite de différents visiteurs, qui peuvent effectuer des opérations spécifiques
 * en fonction du type de tâche visitée.
 */
public interface TaskVisitor {
    /**
     * Méthode pour visiter une SimpleTask.
     *
     * @param task la SimpleTask à visiter.
     */
    void visit(SimpleTask task);
    /**
     * Méthode pour visiter une ComplexTask.
     *
     * @param task la ComplexTask à visiter.
     */
    void visit(ComplexTask task);
}
