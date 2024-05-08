package fr.univrouen.tasklistObserver;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.Priority;
import fr.univrouen.task.TaskComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.time.LocalDate;
import java.util.List;

public class TreeViewTask implements TaskListObserver {

    private TreeTableView<TaskComponent> treeTableView = new TreeTableView<>();
    private TreeItem<TaskComponent> root;

    public TreeViewTask(){

        // Colonne pour la description de la tâche
        TreeTableColumn<TaskComponent, String> descriptionColumn = new TreeTableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getDescription()));

        // Colonne pour la date d'échéance de la tâche
        TreeTableColumn<TaskComponent, LocalDate> dueDateColumn = new TreeTableColumn<>("Date d'échéance");
        dueDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getDueDate()));

        // Colonne pour la durée estimée de la tâche
        TreeTableColumn<TaskComponent, Integer> estimatedDurationColumn = new TreeTableColumn<>("Durée estimée");
        estimatedDurationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getEstimatedDate()));

        // Colonne pour la priorité de la tâche
        TreeTableColumn<TaskComponent, Priority> priorityColumn = new TreeTableColumn<>("Priorité");
        priorityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getPriority()));

        TreeTableColumn<TaskComponent, Float> progressColumn = getTaskComponentFloatTreeTableColumn();

        // Définir la largeur préférée de la colonne en pixels
        descriptionColumn.setPrefWidth(290);


        treeTableView.getColumns().add(descriptionColumn);
        treeTableView.getColumns().add(dueDateColumn);
        treeTableView.getColumns().add(estimatedDurationColumn);
        treeTableView.getColumns().add(priorityColumn);
        treeTableView.getColumns().add(progressColumn);

        root = new TreeItem<>();
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }

    private TreeTableColumn<TaskComponent, Float> getTaskComponentFloatTreeTableColumn() {
        TreeTableColumn<TaskComponent, Float> progressColumn = new TreeTableColumn<>("Progression");
        progressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getProgress()));

        // Définission CellFactory pour personnaliser l'affichage de la progression
        progressColumn.setCellFactory(column -> {
            return new TreeTableCell<TaskComponent, Float>() {
                @Override
                protected void updateItem(Float progress, boolean empty) {
                    super.updateItem(progress, empty);
                    if (empty || progress == null) {
                        setText(null);
                    } else {
                        // Affichez la progression sous forme de pourcentage
                        setText(String.format("%.0f%%", progress * 100));
                    }
                }
            };
        });
        return progressColumn;
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
