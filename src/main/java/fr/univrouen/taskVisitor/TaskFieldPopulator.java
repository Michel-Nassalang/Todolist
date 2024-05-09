package fr.univrouen.taskVisitor;

import fr.univrouen.task.ComplexTask;
import fr.univrouen.task.Priority;
import fr.univrouen.task.SimpleTask;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * La classe TaskFieldPopulator est un visiteur chargé de remplir les champs d'une tâche
 * dans une interface utilisateur graphique (GUI), tels que des champs de texte, des choix,
 * etc., avec les données de la tâche.
 */
public class TaskFieldPopulator implements TaskVisitor {
    private final TextField descriptionField;
    private final DatePicker dueDateField;
    private final TextField estimatedDurationField;
    private final ChoiceBox<Priority> priorityChoiceBox;
    private final ChoiceBox<String> stateProgressChoiceBox;
    private final TextField subTasksCountField;
    private final ChoiceBox<String> taskTypeChoiceBox;

    /**
     * Constructeur de la classe TaskFieldPopulator.
     *
     * @param descriptionField      le champ de texte pour la description de la tâche.
     * @param dueDateField          le champ de sélection de date pour la date d'échéance de la tâche.
     * @param estimatedDurationField le champ de texte pour la durée estimée de la tâche.
     * @param priorityChoiceBox     la boîte de choix pour la priorité de la tâche.
     * @param stateProgressChoiceBox la boîte de choix pour l'état de progression de la tâche.
     * @param subTasksCountField    le champ de texte pour afficher le nombre de sous-tâches pour une tâche complexe.
     * @param taskTypeChoiceBox     la boîte de choix pour le type de tâche (simple ou complexe).
     */
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

    /**
     * Méthode pour visiter une SimpleTask et remplir les champs GUI avec ses données.
     *
     * @param task la SimpleTask à traiter.
     */
    @Override
    public void visit(SimpleTask task) {
        descriptionField.setText(task.getDescription());
        dueDateField.setValue(task.getDueDate());
        estimatedDurationField.setText(String.valueOf(task.getEstimatedDate()));
        priorityChoiceBox.setValue(task.getPriority());
        stateProgressChoiceBox.setValue((task.getProgress() == 1) ? "Réalisé" : "Non réalisé");
        taskTypeChoiceBox.setValue("Tache simple");
    }

    /**
     * Méthode pour visiter une ComplexTask et remplir les champs GUI avec ses données.
     *
     * @param task la ComplexTask à traiter.
     */
    @Override
    public void visit(ComplexTask task) {
        subTasksCountField.setText(String.valueOf(task.getSubTasks().size()));
        priorityChoiceBox.setValue(task.getPriority());
        taskTypeChoiceBox.setValue("Tache complexe");
    }

}

