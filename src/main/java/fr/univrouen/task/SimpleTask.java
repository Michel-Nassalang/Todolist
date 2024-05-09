package fr.univrouen.task;

import fr.univrouen.taskVisitor.TaskVisitor;

import java.time.LocalDate;

/**
 * Représente une tâche simple.
 */
public class SimpleTask implements TaskComponent {
	private String desc; // Description de la tâche
	private LocalDate dueDate; // Date d'échéance de la tâche
	private Priority priority; // Priorité de la tâche
	private Integer estimdate; // Durée estimée de la tâche en nombre de jours
	private Boolean progress; // Progression de la tâche (complétée ou non)

	/**
	 * Constructeur par défaut.
	 */
	public SimpleTask() {}
	/**
	 * Constructeur avec paramètres.
	 *
	 * @param desc      Description de la tâche
	 * @param dueDate   Date d'échéance de la tâche
	 * @param priority  Priorité de la tâche
	 * @param estimdate Durée estimée de la tâche en nombre de jours
	 * @param progress  Progression de la tâche (complétée ou non)
	 */
	public SimpleTask(String desc, LocalDate dueDate, Priority priority, Integer estimdate, Boolean progress){
		this.desc = desc;
		this.dueDate = dueDate;
		this.priority = priority;
		this.estimdate = estimdate;
		this.progress = progress;
	}

	/**
	 * @return La description de la tâche.
	 */
	@Override
	public String getDescription() {
		return desc;
	}

	/**
	 * Modifie la description de la tâche.
	 *
	 * @param desc La nouvelle description de la tâche.
	 */
	public void setDescription(String desc) {
		this.desc = desc;
	}

	/**
	 * @return La date d'échéance de la tâche.
	 */
	@Override
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * Modifie la date d'échéance de la tâche.
	 *
	 * @param duaDate La nouvelle date d'échéance de la tâche.
	 */
	public void setDueDate(LocalDate duaDate) {
		this.dueDate = duaDate;
	}

	/**
	 * @return La priorité de la tâche.
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}

	/**
	 * Modifie la priorité de la tâche.
	 *
	 * @param priority La nouvelle priorité de la tâche.
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * @return La durée estimée de la tâche en nombre de jours.
	 */
	@Override
	public Integer getEstimatedDate() {
		return estimdate;
	}

	/**
	 * Modifie la durée estimée de la tâche.
	 *
	 * @param estimdate La nouvelle durée estimée de la tâche en nombre de jours.
	 */
	public void setEstimatedDate(Integer estimdate) {
		this.estimdate = estimdate;
	}

	/**
	 * @return La progression de la tâche en pourcentage.
	 */
	@Override
	public float getProgress() {
		return (progress) ? 1 : 0;
	}

	/**
	 * Modifie la progression de la tâche.
	 *
	 * @param progress La nouvelle progression de la tâche.
	 */
	public void setProgress(float progress) {
		this.progress = (progress != 0);
	}

	/**
	 * Accepte un visiteur pour permettre le pattern Visitor.
	 *
	 * @param visitor Le visiteur à accepter.
	 */
	@Override
	public void accept(TaskVisitor visitor) {
		visitor.visit(this);
	}
	
}
