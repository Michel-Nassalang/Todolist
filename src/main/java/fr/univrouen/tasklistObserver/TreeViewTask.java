package fr.univrouen.tasklistObserver;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.TaskComponent;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.util.List;

public class TreeViewTask implements TaskListObserver {

    private TreeTableView<TaskComponent> treeTableView = new TreeTableView<>();
    private TreeItem<TaskComponent> root;

    public TreeViewTask(){
        TreeTableColumn<TaskComponent, String> column = new TreeTableColumn<>("Liste de taches");
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getDescription()));

        treeTableView.getColumns().add(column);
        root = new TreeItem<>();
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }


    private void addComplexTaskToTree(ComplexTask complexTask, TreeItem<TaskComponent> parent) {
        TreeItem<TaskComponent> taskItem = new TreeItem<>(complexTask);
        parent.getChildren().add(taskItem);
        for (TaskComponent subTask : complexTask.getSubTasks()) {
            if (subTask instanceof ComplexTask) {
                // Si la sous-tâche est une ComplexTask, récursivement ajouter son arborescence
                addComplexTaskToTree((ComplexTask) subTask, taskItem);
            } else {
                // Si la sous-tâche est une SimpleTask, ajouter à l'arborescence de la ComplexTask
                addSimpleTaskToTree(subTask, taskItem);
            }
        }
    }

    private void addSimpleTaskToTree(TaskComponent simpleTask, TreeItem<TaskComponent> parent) {
        TreeItem<TaskComponent> taskItem = new TreeItem<>(simpleTask);
        parent.getChildren().add(taskItem);
    }

    public TreeTableView<TaskComponent> getTreeTableView(){
        return treeTableView;
    }
    public void setTreeTableView(TreeTableView<TaskComponent> treeTableView){
        this.treeTableView = treeTableView;
    }
    public TreeItem<TaskComponent> getRoot(){
        return root;
    }
    public void setRoot(TreeItem<TaskComponent> root){
        this.root = root;
    }

    @Override
    public void update(List<TaskComponent> tasks) {
        // Effacer les anciennes tâches
        root.getChildren().clear();
        // Ajouter les nouvelles tâches
        for (TaskComponent task : tasks) {
            if (task instanceof ComplexTask) {
                addComplexTaskToTree((ComplexTask) task, root);
            } else {
                addSimpleTaskToTree(task, root);
            }
        }
    }
}
