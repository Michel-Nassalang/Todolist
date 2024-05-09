package fr.univrouen.ui;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.Priority;
import fr.univrouen.task.SimpleTask;
import fr.univrouen.task.TaskComponent;
import fr.univrouen.taskVisitor.TaskFieldPopulator;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * La classe TaskEditorDialog est responsable de la création d'une boîte de dialogue pour éditer les détails
 * d'une tâche.
 */
public class TaskEditorDialog {

    /**
     * Crée une boîte de dialogue pour éditer les détails d'une tâche.
     *
     * @param task la tâche à éditer, ou null pour créer une nouvelle tâche.
     * @return le dialogue créé pour éditer les détails de la tâche.
     */
    public static Dialog<TaskComponent> createTaskDialog(TaskComponent task){
        Dialog<TaskComponent> dialog = new Dialog<>();
        ChoiceBox<String> taskTypeChoiceBox = new ChoiceBox<>();
        taskTypeChoiceBox.getItems().addAll("Tache simple", "Tache complexe");

        ChoiceBox<Priority> priorityChoiceBox = new ChoiceBox<>();
        priorityChoiceBox.getItems().addAll(Priority.values());
        priorityChoiceBox.setValue(Priority.NORMAL);

        TextField subTasksCountField = new TextField();
        TextField descriptionField = new TextField();
        DatePicker dueDateField = new DatePicker();
        TextField estimatedDurationField = new TextField();
        ChoiceBox<String> stateprogress = new ChoiceBox<>();
        stateprogress.getItems().addAll("Réalisé", "Non réalisé");

        Label tasklab = new Label("Type de tache:");
        Label desclab = new Label("Description:");
        Label subtasklab = new Label("Nombre de sous taches:");
        Label estimlab = new Label("Durée estimée:");
        Label priolab = new Label("Priorité:");
        Label duedalab = new Label("Date d'échéance:");
        Label proglab = new Label("Etat de réalisation:");

        dueDateField.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(60);

        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(50);

        grid.getColumnConstraints().addAll(column1, column2);
        grid.getRowConstraints().addAll(row1, row2);

        grid.add(tasklab, 0, 0);
        grid.add(taskTypeChoiceBox, 1, 0);
        grid.add(desclab, 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(duedalab, 0, 2);
        grid.add(dueDateField, 1, 2);
        grid.add(estimlab, 0, 3);
        grid.add(estimatedDurationField, 1, 3);
        grid.add(priolab, 0, 4);
        grid.add(priorityChoiceBox, 1, 4);
        grid.add(proglab, 0, 5);
        grid.add(stateprogress, 1, 5);
        grid.add(subtasklab, 0, 6);
        grid.add(subTasksCountField, 1, 6);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if(taskTypeChoiceBox.getValue().equals("Tache complexe")){
                    if (!subTasksCountField.getText().isEmpty() && !subTasksCountField.getText().isBlank()
                    && priorityChoiceBox.getValue().describeConstable().isPresent()) {
                        int subTasksCount = Integer.parseInt(subTasksCountField.getText());
                        Priority priority = priorityChoiceBox.getValue();
                        ComplexTask complexTask = new ComplexTask();
                        complexTask.setPriority(priority);
                        for (int i = 0; i < subTasksCount; i++) {
                            Dialog<TaskComponent> subTaskDialog = createTaskDialog(null);
                            subTaskDialog.setTitle("Ajout Sous tâche");
                            subTaskDialog.setHeaderText("Entrer les détails du sous tâche");
                            Optional<TaskComponent> result = subTaskDialog.showAndWait();
                            if (result.isPresent()) {
                                TaskComponent taski = result.get();
                                complexTask.addSubTask(taski);
                            } else {
                                return null; // Returning null to abort task creation
                            }
                        }
                        return complexTask;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText(null);
                        alert.setContentText("Donner un nombre de sous-taches valides");
                        alert.showAndWait();
                        return null; // Returning null to abort task creation
                    }
                }else {
                    if (!estimatedDurationField.getText().isEmpty() && !descriptionField.getText().isEmpty()
                    && dueDateField.getValue() != null && priorityChoiceBox.getValue() != null
                    && !stateprogress.getValue().isEmpty()) {
                        String description = descriptionField.getText();
                        LocalDate dueDate = dueDateField.getValue();
                        Priority priority = priorityChoiceBox.getValue();
                        boolean progress = !Objects.equals(stateprogress.getValue(), "Non réalisé");
                        int estimatedDuration = Integer.parseInt(estimatedDurationField.getText());
                        return new SimpleTask(description, dueDate, priority, estimatedDuration, progress);
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText(null);
                        alert.setContentText("Veuillez donnez les caractéristiques de la tache");
                        alert.showAndWait();
                        return null; // Returning null to abort task creation
                    }
                }
            }
            return null;
        });

        taskTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Tache complexe")) {
                subtasklab.setVisible(true);
                subTasksCountField.setVisible(true);
                priolab.setVisible(true);
                priorityChoiceBox.setVisible(true);
                duedalab.setVisible(false);
                dueDateField.setVisible(false); // Masquer le champ de date d'échéance
                estimlab.setVisible(false);
                estimatedDurationField.setVisible(false); // Masquer le champ de durée estimée
                desclab.setVisible(false);
                descriptionField.setVisible(false);
                stateprogress.setVisible(false);
                proglab.setVisible(false);

            } else {
                subtasklab.setVisible(false);
                subTasksCountField.setVisible(false);
                priolab.setVisible(true);
                priorityChoiceBox.setVisible(true);
                duedalab.setVisible(true);
                dueDateField.setVisible(true); // Masquer le champ de date d'échéance
                estimlab.setVisible(true);
                estimatedDurationField.setVisible(true); // Masquer le champ de durée estimée
                desclab.setVisible(true);
                descriptionField.setVisible(true);
                stateprogress.setVisible(true);
                proglab.setVisible(true);
            }
        });

        if (task != null) {
            TaskFieldPopulator fieldSetter = new TaskFieldPopulator(descriptionField, dueDateField, estimatedDurationField, priorityChoiceBox, stateprogress, subTasksCountField, taskTypeChoiceBox);
            task.accept(fieldSetter);
        }
        return dialog;
    }

}
