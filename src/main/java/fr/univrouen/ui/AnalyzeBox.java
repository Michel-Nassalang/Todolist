package fr.univrouen.ui;

import fr.univrouen.analyzer.TaskAnalyzer;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistObserver.TreeViewTask;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * La classe AnalyzeBox est responsable de la création des boutons et de la gestion des événements associés
 * pour l'analyse des tâches.
 */
public class AnalyzeBox {
    private final VBox analyzeButtons;

    /**
     * Constructeur de la classe AnalyzeBox.
     *
     * @param taskAnalyzer l'instance de TaskAnalyzer pour l'analyse des tâches.
     * @param treeViewTask l'instance de TreeViewTask pour afficher les tâches dans l'interface utilisateur.
     * @param stage        l'instance de Stage pour la fenêtre de l'application.
     */
    public AnalyzeBox(TaskAnalyzer taskAnalyzer, TreeViewTask treeViewTask, Stage stage) {
        analyzeButtons = new VBox();
        analyzeButtons.setSpacing(10);
        analyzeButtons.setPadding(new Insets(20));

        Button loadButton = new Button("Lire");
        Button incompleteButton = new Button("Non complétés");
        Button orderButton = new Button("Ordonner");

        analyzeButtons.getChildren().addAll(loadButton, incompleteButton, orderButton);
        treeViewTask.getRoot().getChildren().clear();
        loadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Lecture de liste de taches");
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile!=null){
                String filename = selectedFile.getAbsolutePath();
                if(!filename.isEmpty()){
                    boolean inLoaded = taskAnalyzer.loadFromFile(filename, treeViewTask);
                    if (!inLoaded) {
                        AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Sauvegarde echouée");
                    }
                }
            }
        });

        incompleteButton.setOnAction(event -> {
            List<TaskComponent> incompleteTasks = taskAnalyzer.getTop5IncompleteTasks();
            if (incompleteTasks != null && !incompleteTasks.isEmpty()) {
                //Création du dialog pour afficher les taches incomplètes
                Dialog<Void> dialog = TaskAnalyzerDialog.createTasksDialog(incompleteTasks);
                dialog.setTitle("Tâches incomplètes");
                dialog.setHeaderText("Liste des 5 tâches incomplètes avec les dates d'échéance les plus proches :");
                dialog.showAndWait();
            } else {
                AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Liste vide");
            }
        });

        orderButton.setOnAction(event -> {
            List<TaskComponent> orderTasks = taskAnalyzer.orderTaskList();
            if (orderTasks != null) {
                //Création du dialog pour l'affichage des taches ordonées
                Dialog<Void> dialog = TaskAnalyzerDialog.createTasksDialog(orderTasks);
                dialog.setTitle("Tâches ordonnées");
                dialog.setHeaderText("Liste des tâches ordonnées par ordre croissant de date :");
                dialog.showAndWait();
            } else {
                AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Liste vide");
            }
        });

    }

    /**
     * Renvoie la VBox contenant les boutons d'analyse.
     *
     * @return la VBox contenant les boutons d'analyse.
     */
    public VBox getButtonBox() {
        return analyzeButtons;
    }

}
