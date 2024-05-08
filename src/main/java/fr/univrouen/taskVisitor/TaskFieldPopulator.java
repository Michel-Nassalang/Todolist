package fr.univrouen.taskVisitor;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.Priority;
import fr.univrouen.task.SimpleTask;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TaskFieldPopulator implements TaskVisitor {
    private final TextField descriptionField;
    private final DatePicker dueDateField;
    private final TextField estimatedDurationField;
    private final ChoiceBox<Priority> priorityChoiceBox;
    private final ChoiceBox<String> stateProgressChoiceBox;
    private final TextField subTasksCountField;
    private final ChoiceBox<String> taskTypeChoiceBox;

    public TaskFieldPopulator(TextField descriptionField, DatePicker dueDateField,
                           TextField estimatedDurationField, ChoiceBox<Priority> priorityChoiceBox,
                           ChoiceBox<String> stateProgressChoiceBox, TextField subTasksCountField,
                           ChoiceBox<String> taskTypeChoiceBox) {
        this.descriptionField = descriptionField;
        this.dueDateField = dueDateField;
        this.estimatedDurationField = estimatedDurationField;
        this.priorityChoiceBox = priorityChoiceBox;
        this.stateProgressChoiceBox = stateProgressChoiceBox;
        this.subTasksCountField = subTasksCountField;
        this.taskTypeChoiceBox = taskTypeChoiceBox;
    }

    @Override
    public void visit(SimpleTask task) {
        descriptionField.setText(task.getDescription());
        dueDateField.setValue(task.getDueDate());
        estimatedDurationField.setText(String.valueOf(task.getEstimatedDate()));
        priorityChoiceBox.setValue(task.getPriority());
        stateProgressChoiceBox.setValue((task.getProgress() == 1) ? "Réalisé" : "Non réalisé");
        taskTypeChoiceBox.setValue("Tache simple");
    }

    @Override
    public void visit(ComplexTask task) {
        subTasksCountField.setText(String.valueOf(task.getSubTasks().size()));
        priorityChoiceBox.setValue(task.getPriority());
        taskTypeChoiceBox.setValue("Tache complexe");
    }

}

