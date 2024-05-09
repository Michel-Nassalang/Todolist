package fr.univrouen.editor;

import fr.univrouen.task.TaskComponent;
import fr.univrouen.ui.EditBox;
import fr.univrouen.tasklistObserver.TreeViewTask;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * L'application TaskEditorApp est une application JavaFX pour gérer les tâches à l'aide d'un éditeur seulement
 */
public class TaskEditorApp extends Application {

    private TaskEditor taskEditor = TaskEditor.getInstance();
    private BorderPane mainPane;

    /**
     * Méthode principale pour démarrer l'application JavaFX.
     *
     * @param stage la fenêtre principale de l'application.
     * @throws IOException en cas d'erreur d'entrée/sortie.
     */
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Todolist");

        // Création des boutons
        Button editButton = new Button("Editeur");

        // Création du conteneur principal
        mainPane = new BorderPane();
        mainPane.setPadding(new Insets(20));

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(editButton);
        mainPane.setLeft(buttonBox);

        TreeViewTask treeViewTask = new TreeViewTask();

        TreeTableView<TaskComponent> treeTableView = treeViewTask.getTreeTableView();


        // Ajout du TreeTableView pour afficher les informations des tâches
        mainPane.setCenter(treeTableView);

        editButton.setOnAction(event -> {
            EditBox editBox = new EditBox(taskEditor, treeViewTask, stage);
            VBox editButtons = editBox.getButtonBox();

            mainPane.setRight(editButtons);
        });


        stage.setScene(new Scene(mainPane, 900, 600));
        stage.show();
    }

    /**
     * Méthode principale pour lancer l'application.
     *
     * @param args les arguments de la ligne de commande (non utilisés dans cette application).
     */
    public static void main(String[] args) {
        launch();
    }
}
