package fr.univrouen.ui;

import fr.univrouen.analyzer.TaskAnalyzer;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistObserver.TreeViewTask;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class AnalyzeBox {
    private final VBox analyzeButtons;

    public AnalyzeBox(TaskAnalyzer taskAnalyzer, TreeViewTask treeViewTask, Stage stage) {
        analyzeButtons = new VBox();
        analyzeButtons.setSpacing(10);
        analyzeButtons.setPadding(new Insets(20));

        Button loadButton = new Button("Lire");
        Button incompleteButton = new Button("Non complétés");
        Button orderButton = new Button("Ordonner");

        analyzeButtons.getChildren().addAll(loadButton, incompleteButton, orderButton);

        loadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Lecture de liste de taches");
            File selectedFile = fileChooser.showOpenDialog(stage);
            if(selectedFile!=null){
                String filename = selectedFile.getAbsolutePath();
                if(!filename.isEmpty()){
                    boolean inLoaded = taskAnalyzer.loadFromFile(filename, treeViewTask);
                    if (inLoaded) {
                        AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "Lecture de liste de tâches avec succès");
                    } else {
                        AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Sauvegarde echouée");
                    }
                }
            }
        });

        incompleteButton.setOnAction(event -> {
            List<TaskComponent> incompleteTasks = taskAnalyzer.getTop5IncompleteTasks();
            if (incompleteTasks != null) {
                AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "Recupération de la liste des 5 tâches, non complétées, dont les dates d’échéance sont les plus faibles");
            } else {
                AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Liste vide");
            }
        });

        orderButton.setOnAction(event -> {
            List<TaskComponent> orderTasks = taskAnalyzer.orderTaskList();
            if (orderTasks != null) {
                AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "Ordonnancement des taches de la liste");
            } else {
                AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Liste vide");
            }
        });

    }

    public VBox getButtonBox() {
        return analyzeButtons;
    }

}
