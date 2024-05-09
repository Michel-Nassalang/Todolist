package fr.univrouen.taskVisitor;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.SimpleTask;
import fr.univrouen.task.TaskComponent;
import javafx.scene.control.TreeItem;

/**
 * La classe TaskToTree est un visiteur qui convertit une hiérarchie de tâches en une structure
 * d'arbre utilisée généralement pour l'affichage dans une interface utilisateur graphique (GUI).
 */
public class TaskToTree implements TaskVisitor {

    private TreeItem<TaskComponent> parent;

    /**
     * Constructeur de la classe TaskToTree.
     *
     * @param parent le nœud parent de l'arbre où seront ajoutées les tâches converties.
     */
    public TaskToTree(TreeItem<TaskComponent> parent) {
        this.parent = parent;
    }

    /**
     * Méthode pour visiter une SimpleTask et l'ajouter en tant que nœud à l'arbre.
     *
     * @param task la SimpleTask à convertir en nœud d'arbre.
     */
    @Override
    public void visit(SimpleTask task) {
        TreeItem<TaskComponent> taskItem = new TreeItem<>(task);
        parent.getChildren().add(taskItem);
    }

    /**
     * Méthode pour visiter une ComplexTask et l'ajouter en tant que nœud à l'arbre.
     * Cette méthode appelle également récursivement la méthode accept() sur les sous-tâches
     * de la tâche complexe pour les ajouter également à l'arbre.
     *
     * @param task la ComplexTask à convertir en nœud d'arbre.
     */
    @Override
    public void visit(ComplexTask task) {
        TreeItem<TaskComponent> taskItem = new TreeItem<>(task);
        parent.getChildren().add(taskItem);
        for (TaskComponent subTask : task.getSubTasks()) {
//            Appel récursif pour traiter les sous-tâches
            TaskToTree taskToTree = new TaskToTree(taskItem);
            subTask.accept(taskToTree);
        }
    }
}
