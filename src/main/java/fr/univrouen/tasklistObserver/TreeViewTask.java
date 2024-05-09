package fr.univrouen.tasklistObserver;

import fr.univrouen.task.Priority;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.taskVisitor.TaskToTree;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.time.LocalDate;
import java.util.List;

/**
 * La classe TreeViewTask est une implémentation de l'interface TaskListObserver
 * qui affiche les tâches sous forme d'arborescence dans une interface utilisateur
 * graphique JavaFX.
 */
public class TreeViewTask implements TaskListObserver {

    private TreeTableView<TaskComponent> treeTableView = new TreeTableView<>();
    private TreeItem<TaskComponent> root;

    /**
     * Constructeur de la classe TreeViewTask.
     * Initialise les colonnes de l'arborescence et définit la racine.
     */
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

    /**
     * Méthode pour obtenir la TableView de l'arborescence.
     *
     * @return la TreeTableView de l'arborescence.
     */
    public TreeTableView<TaskComponent> getTreeTableView(){
        return treeTableView;
    }

    /**
     *
     * @param treeTableView classe contenant les conteneurs des taches visuelles
     */
    public void setTreeTableView(TreeTableView<TaskComponent> treeTableView){
        this.treeTableView = treeTableView;
    }

    /**
     * Méthode pour obtenir la racine de l'arborescence.
     *
     * @return la racine de l'arborescence.
     */
    public TreeItem<TaskComponent> getRoot(){
        return root;
    }

    /**
     *
     * @param root element central d'affichage des taches dans l'interface
     */
    public void setRoot(TreeItem<TaskComponent> root){
        this.root = root;
    }

    /**
     * Méthode appelée lorsqu'il y a une mise à jour dans la liste de tâches.
     * Efface les anciennes tâches et ajoute les nouvelles à l'arborescence.
     *
     * @param tasks la liste mise à jour des tâches.
     */
    @Override
    public void update(List<TaskComponent> tasks) {
        // Effacer les anciennes tâches
        root.getChildren().clear();
        // Ajouter les nouvelles tâches
        TaskToTree visitor = new TaskToTree(root);
        for (TaskComponent task : tasks) {
            task.accept(visitor);
        }
    }
}
