package fr.univrouen.ui;

import fr.univrouen.task.Priority;
import fr.univrouen.task.TaskComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

/**
 * La classe TaskAnalyzerDialog est responsable de la création d'une boîte de dialogue pour afficher les tâches
 * incomplètes.
 */
public class TaskAnalyzerDialog {
    /**
     * Crée une boîte de dialogue pour afficher les tâches incomplètes.
     *
     * @param tasks la liste des tâches incomplètes à afficher dans la boîte de dialogue.
     * @return le dialogue créé pour afficher les tâches incomplètes.
     */
    public static Dialog<Void> createTasksDialog(List<TaskComponent> tasks) {
        // Créer un TreeTableView pour afficher les tâches incomplètes
        TreeTableView<TaskComponent> treeTableView = new TreeTableView<>();
        TreeItem<TaskComponent> root = new TreeItem<>();
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

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

        // Colonne pour la progression de la tâche
        TreeTableColumn<TaskComponent, String> progressColumn = new TreeTableColumn<>("Progression");
        progressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(String.valueOf(String.format("%.0f%%", cellData.getValue().getValue().getProgress()*100))));

        // Ajouter les colonnes au TreeTableView
        treeTableView.getColumns().addAll(descriptionColumn, dueDateColumn, estimatedDurationColumn, priorityColumn, progressColumn);

        // Définir la largeur préférée des colonnes
        descriptionColumn.setPrefWidth(200);
        dueDateColumn.setPrefWidth(150);
        estimatedDurationColumn.setPrefWidth(150);
        priorityColumn.setPrefWidth(100);
        progressColumn.setPrefWidth(100);

        // Ajouter les tâches incomplètes à la racine du TreeTableView
        tasks.forEach(task -> root.getChildren().add(new TreeItem<>(task)));

        // Créer une VBox pour contenir le TreeTableView
        VBox rootPane = new VBox(treeTableView);
        rootPane.setPrefSize(800, 300); // Définir la taille préférée de la VBox

        // Créer un dialogue pour afficher le TreeTableView
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Tâches incomplètes");
        dialog.setHeaderText("Liste des 5 tâches incomplètes avec les dates d'échéance les plus proches :");
        dialog.getDialogPane().setContent(rootPane);

        // Ajouter un bouton "OK" pour fermer le dialogue
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        return dialog;
    }
}
