package fr.univrouen.ui;

import fr.univrouen.editor.TaskEditor;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistObserver.TreeViewTask;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * La classe EditBox est responsable de la création des boutons et de la gestion des événements associés
 * pour l'édition des tâches.
 */
public class EditBox {

        private final VBox buttonBox;

    /**
     * Constructeur de la classe EditBox.
     *
     * @param taskEditor   l'instance de TaskEditor pour l'édition des tâches.
     * @param treeViewTask l'instance de TreeViewTask pour afficher les tâches dans l'interface utilisateur.
     * @param stage        l'instance de Stage pour la fenêtre de l'application.
     */
        public EditBox(TaskEditor taskEditor, TreeViewTask treeViewTask, Stage stage) {
            buttonBox = new VBox();
            buttonBox.setSpacing(10);
            buttonBox.setPadding(new Insets(20));
            Button createTaskList = new Button("Créer tasklist");
            Button addTaskButton = new Button("Ajouter");
            Button removeTaskButton = new Button("Supprimer");
            Button modifyButton = new Button("Modifier");
            Button saveButton = new Button("Sauvegarder");
            Button loadButton = new Button("Charger");

            buttonBox.getChildren().addAll(createTaskList, addTaskButton, removeTaskButton, modifyButton, saveButton, loadButton);
            treeViewTask.getRoot().getChildren().clear();
            TreeTableView<TaskComponent> treeTableView = treeViewTask.getTreeTableView();
            TreeItem<TaskComponent> root = treeViewTask.getRoot();

            // Définition des actions des boutons
            createTaskList.setOnAction(event -> {
                    boolean success = taskEditor.createNewTask(treeViewTask);
                    if (success) {
                        AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "Nouvelle liste de taches créée avec succès");
                    } else {
                        AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Création de liste de taches échouée");
                    }
            });

            addTaskButton.setOnAction(event -> {
                Dialog<TaskComponent> inputDialog = TaskEditorDialog.createTaskDialog(null);
                Optional<TaskComponent> result = inputDialog.showAndWait();
                result.ifPresent(task -> {
                    boolean success = taskEditor.addTask(task);
                    if (success) {
                        AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "Ajout de tache réussi");
                    } else {
                        AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Ajout de tache échoué. Vérifier la présence de la liste de taches");
                    }
                });
            });

            removeTaskButton.setOnAction(event -> {
                TreeItem<TaskComponent> selectedItem = treeTableView.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    AlertUI.showAlert(Alert.AlertType.WARNING, "Avertissement", "Selectionner la tache à supprimer");
                } else {
                    TaskComponent selectedTask = selectedItem.getValue();
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText("Etes vous sur de vouloir supprimer cette tache?");
                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Supprimer la ComplexTask ainsi que toutes les SimpleTask qui la composent
                        boolean success = taskEditor.removeTask(selectedTask);
                        if(success){
                            if(root.getParent() != null){
                                root.getParent().getChildren().remove(selectedItem);
                            }
                            AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "La tache a été supprimé avec succès.");
                        }else{
                            AlertUI.showAlert(Alert.AlertType.INFORMATION, "Echec", "La tache n'a pas été supprimé.");
                        }
                    }
                }
            });

            modifyButton.setOnAction(event -> {
                TreeItem<TaskComponent> selectedItem = treeTableView.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    AlertUI.showAlert(Alert.AlertType.WARNING, "Avertissement", "Selectionner la tache à modifier");
                } else {
                    TaskComponent selectedTask = selectedItem.getValue();
                    Dialog<TaskComponent> inputDialog = new TaskEditorDialog().createTaskDialog(selectedTask);
                    Optional<TaskComponent> result = inputDialog.showAndWait();
                    result.ifPresent(modifiedTask -> {
                        boolean success = taskEditor.modifyTask(selectedTask, modifiedTask);
                        if (success) {
                            AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "Tache modifiée avec succès");
                        } else {
                            AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Modification de tache échouée.");
                        }
                    });
                }
            });


            saveButton.setOnAction(event -> {
                if (root.getChildren().isEmpty()) {
                    AlertUI.showAlert(Alert.AlertType.WARNING, "Avertissement", "Vous n'avez pas de taches disponibles à sauvegarder");
                } else {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Sauvegarde de liste de taches");
                    File selectedFile = fileChooser.showSaveDialog(stage);

                    boolean inSaved = taskEditor.saveToFile(selectedFile.getAbsolutePath());
                    if (inSaved) {
                        AlertUI.showAlert(Alert.AlertType.INFORMATION, "Succès", "La liste de tache est sauvegardée.");
                    } else {
                        AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Sauvegarde echouée");
                    }
                }
            });

            loadButton.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chargement de liste de taches");
                File selectedFile = fileChooser.showOpenDialog(stage);
                if(selectedFile!=null) {
                    String filename = selectedFile.getAbsolutePath();
                    if (!filename.isEmpty()) {
                        boolean inLoaded = taskEditor.loadFromFile(selectedFile.getAbsolutePath(), treeViewTask);
                        if (!inLoaded) {
                            AlertUI.showAlert(Alert.AlertType.ERROR, "Erreur", "Chargement echoué");
                        }
                    }
                }
            });

        }

    /**
     * Renvoie la VBox contenant les boutons d'édition.
     *
     * @return la VBox contenant les boutons d'édition.
     */
        public VBox getButtonBox() {
            return buttonBox;
        }


    }
