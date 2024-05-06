package fr.univrouen.ui;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.Priority;
import fr.univrouen.task.SimpleTask;
import fr.univrouen.task.TaskComponent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TaskInputDialog extends Dialog<TaskComponent> {

    private TextField descriptionField;
    private DatePicker dueDateField;
    private TextField estimatedDurationField;
    private ChoiceBox<String> taskTypeChoiceBox;
    private TextField subTasksCountField;
    private ChoiceBox<Priority> priorityChoiceBox;

    public TaskInputDialog() {
        this(null); // Appel du constructeur avec une tâche null pour créer une nouvelle tâche
    }


    public TaskInputDialog(TaskComponent existingTask) {
        setTitle(existingTask == null ? "Add New Task" : "Modify Task");
        setHeaderText(existingTask == null ? "Enter Task Details" : "Modify Task Details");

        taskTypeChoiceBox = new ChoiceBox<>();
        taskTypeChoiceBox.getItems().addAll("Tache simple", "Tache complexe");
        if (existingTask instanceof SimpleTask) {
            taskTypeChoiceBox.setValue("Tache simple");
        } else if (existingTask instanceof ComplexTask) {
            taskTypeChoiceBox.setValue("Tache complexe");
        }

        descriptionField = new TextField();
        dueDateField = new DatePicker();
        estimatedDurationField = new TextField();
        priorityChoiceBox = new ChoiceBox<>();
        priorityChoiceBox.getItems().addAll(Priority.values());
        priorityChoiceBox.setValue(Priority.NORMAL);
        subTasksCountField = new TextField();
        subTasksCountField.setVisible(false);






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

        grid.add(new Label("Type de tache:"), 0, 0);
        grid.add(taskTypeChoiceBox, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Date d'échéance:"), 0, 2);
        grid.add(dueDateField, 1, 2);
        grid.add(new Label("Durée estimée:"), 0, 3);
        grid.add(estimatedDurationField, 1, 3);
        grid.add(new Label("Priorité:"), 0, 4);
        grid.add(priorityChoiceBox, 1, 4);
        grid.add(new Label("Nombre de sous taches:"), 0, 5);
        grid.add(subTasksCountField, 1, 5);

        getDialogPane().setContent(grid);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String description = descriptionField.getText();
                LocalDate dueDate = dueDateField.getValue();
                Priority priority = priorityChoiceBox.getValue();
                if (taskTypeChoiceBox.getValue().equals("Tache simple")) {
                    int estimatedDuration = Integer.parseInt(estimatedDurationField.getText());
                    return new SimpleTask(description, dueDate, priority, estimatedDuration, false);
                }

                if (taskTypeChoiceBox.getValue().equals("Tache complexe")) {
                    if (!subTasksCountField.getText().isEmpty() && !subTasksCountField.getText().isBlank()) {
                        int subTasksCount = Integer.parseInt(subTasksCountField.getText());
                        ComplexTask complexTask = new ComplexTask();
                        for (int i = 0; i < subTasksCount; i++) {
                            Dialog<TaskComponent> subTaskDialog = createSubTaskDialog();
                            Optional<TaskComponent> result = subTaskDialog.showAndWait();
                            if (result.isPresent()) {
                                TaskComponent task = result.get();
                                if (task instanceof SimpleTask) {
                                    complexTask.addSubTask(task);
                                } else if (task instanceof ComplexTask) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Erreur");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Cannot add a Tache complexe as a subtask.");
                                    alert.showAndWait();
                                    return null; // Returning null to abort task creation
                                }
                            } else {
                                return null; // Returning null to abort task creation
                            }
                        }
                        return complexTask;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter a valid value for Subtasks Count.");
                        alert.showAndWait();
                        return null; // Returning null to abort task creation
                    }
                }
            }
            return null;
        });


        if (existingTask != null) {
            // Préremplir les champs avec les détails de la tâche existante
            if (existingTask instanceof SimpleTask) {
                SimpleTask simpleTask = (SimpleTask) existingTask;
                descriptionField.setText(simpleTask.getDescription());
                dueDateField.setValue(simpleTask.getDueDate());
                estimatedDurationField.setText(String.valueOf(simpleTask.getEstimatedDate()));
                priorityChoiceBox.setValue(simpleTask.getPriority());
                taskTypeChoiceBox.setValue("Tache simple");
            } else if (existingTask instanceof ComplexTask) {
                ComplexTask complexTask = (ComplexTask) existingTask;
                descriptionField.setText(complexTask.getDescription());
                priorityChoiceBox.setValue(complexTask.getPriority());
                taskTypeChoiceBox.setValue("Tache complexe");
                // ... (vous pouvez également préremplir d'autres champs spécifiques aux tâches complexes)
            }
        }

        taskTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Tache complexe")) {
                subTasksCountField.setVisible(true);
                dueDateField.setVisible(false); // Masquer le champ de date d'échéance
                estimatedDurationField.setVisible(false); // Masquer le champ de durée estimée
            } else {
                subTasksCountField.setVisible(false);
                dueDateField.setVisible(true);
                estimatedDurationField.setVisible(true);
            }
        });
    }




    private Dialog<TaskComponent> createSubTaskDialog() {
        Dialog<TaskComponent> subTaskDialog = new Dialog<>();
        subTaskDialog.setTitle("Add Subtask");
        subTaskDialog.setHeaderText("Enter Subtask Details");

        TextField subTaskDescriptionField = new TextField();
        DatePicker subTaskDueDateField = new DatePicker();
        TextField subTaskEstimatedDurationField = new TextField();
        ChoiceBox<Priority> subTaskPriorityChoiceBox = new ChoiceBox<>();
        subTaskPriorityChoiceBox.getItems().addAll(Priority.values());
        subTaskPriorityChoiceBox.setValue(Priority.NORMAL);

        GridPane subTaskGrid = new GridPane();
        subTaskGrid.setHgap(10);
        subTaskGrid.setVgap(10);
        subTaskGrid.setPadding(new Insets(20));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(60);

        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(50);

        subTaskGrid.getColumnConstraints().addAll(column1, column2);
        subTaskGrid.getRowConstraints().addAll(row1, row2);

        subTaskGrid.add(new Label("Description:"), 0, 0);
        subTaskGrid.add(subTaskDescriptionField, 1, 0);
        subTaskGrid.add(new Label("Due Date:"), 0, 1);
        subTaskGrid.add(subTaskDueDateField, 1, 1);
        subTaskGrid.add(new Label("Durée estimée:"), 0, 2);
        subTaskGrid.add(subTaskEstimatedDurationField, 1, 2);
        subTaskGrid.add(new Label("Priority:"), 0, 3);
        subTaskGrid.add(subTaskPriorityChoiceBox, 1, 3);

        subTaskDialog.getDialogPane().setContent(subTaskGrid);
        subTaskDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        subTaskDialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String description = subTaskDescriptionField.getText();
                LocalDate dueDate = subTaskDueDateField.getValue();
                int estimatedDuration = Integer.parseInt(subTaskEstimatedDurationField.getText());
                Priority priority = subTaskPriorityChoiceBox.getValue();
                return new SimpleTask(description, dueDate, priority, estimatedDuration, false);
            }
            return null;
        });


        return subTaskDialog;
    }

}