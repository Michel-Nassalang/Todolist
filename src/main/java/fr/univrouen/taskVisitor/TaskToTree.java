package fr.univrouen.taskVisitor;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.SimpleTask;
import fr.univrouen.task.TaskComponent;
import javafx.scene.control.TreeItem;

public class TaskToTree implements TaskVisitor {

    private TreeItem<TaskComponent> parent;

    public TaskToTree(TreeItem<TaskComponent> parent) {
        this.parent = parent;
    }

    @Override
    public void visit(SimpleTask task) {
        TreeItem<TaskComponent> taskItem = new TreeItem<>(task);
        parent.getChildren().add(taskItem);
    }

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
