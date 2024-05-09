package fr.univrouen.task;

import fr.univrouen.taskVisitor.TaskVisitor;

import java.time.LocalDate;

/**
 * Interface représentant une tâche.
 */
public interface TaskComponent {

	/**
	 * @return La date d'échéance de la tâche.
	 */
	LocalDate getDueDate();

	/**
	 * @return La priorité de la tâche.
	 */
	Priority getPriority();

	/**
	 * @return La durée estimée de la tâche en nombre de jours.
	 */
	Integer getEstimatedDate();

	/**
	 * @return La description courte de la tâche.
	 */
	String getDescription();

	/**
	 * @return La progression de la tâche en pourcentage.
	 */
	float getProgress();

	/**
	 * Accepte un visiteur pour permettre le pattern Visitor.
	 *
	 * @param visitor Le visiteur à accepter.
	 */
	void accept(TaskVisitor visitor);
}
