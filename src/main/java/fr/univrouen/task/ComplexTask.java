package fr.univrouen.task;

import fr.univrouen.taskVisitor.TaskVisitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une tâche complexe composée de plusieurs sous-tâches.
 */
public class ComplexTask implements TaskComponent {

	/**
	 * Liste des sous-tâches formant la tâche complexe
 	 */
	protected List<TaskComponent> subTasks = new ArrayList<TaskComponent>();
	/**
	 * Priorité de la tâche complexe
	 */
	private Priority priority;

	/**
	 * Constructeur par défaut.
	 */
	public ComplexTask() {
	}

	/**
	 * Constructeur avec liste de sous-tâches et priorité.
	 *
	 * @param subTasks Liste des sous-tâches
	 * @param priority Priorité de la tâche complexe
	 */
	public ComplexTask(List<TaskComponent> subTasks, Priority priority) {
		this.subTasks = subTasks;
		this.priority = priority;
	}

	/**
	 * Récupère la date d'échéance la plus tardive parmi toutes les sous-tâches.
	 *
	 * @return La date d'échéance la plus tardive parmi les sous-tâches, ou null si la liste est vide ou nulle.
	 */
	@Override
	public LocalDate getDueDate() {
		List<LocalDate> dates = new ArrayList<LocalDate>();
		// Recupération de toutes les dates des sous tâches
		for (TaskComponent task : subTasks) {
			dates.add(task.getDueDate());
		}
		if (dates == null || dates.isEmpty()) {
			return null; // Retourne null si la liste est vide ou nulle
		}
		LocalDate maxDate = dates.get(0); // Initialise avec la première date de la liste
		// Parcourt la liste pour trouver la plus grande date
		for (LocalDate date : dates) {
			if (date.isAfter(maxDate)) {
				maxDate = date;
			}
		}
		return maxDate;
	}

	/**
	 * Récupère la priorité de la tâche.
	 *
	 * @return La priorité de la tâche.
	 */
	@Override
	public Priority getPriority() {
		return priority;
	}

	/**
	 * Récupère la durée estimée totale de la tâche en nombre de jours.
	 *
	 * @return La durée estimée totale de la tâche en nombre de jours.
	 */
	@Override
	public Integer getEstimatedDate() {
		Integer totalestimdate = 0;
		// Somme des durées estimées
		for (TaskComponent task : subTasks) {
			totalestimdate += task.getEstimatedDate();
		}
		return totalestimdate;
	}

	/**
	 * Récupère la description de la tâche, composée des descriptions de toutes les sous-tâches.
	 *
	 * @return La description de la tâche.
	 */
	@Override
	public String getDescription() {
		String desc = "";
		// Sommation des descriptions des sous taches
		for (TaskComponent task : subTasks) {
			desc += (" " + task.getDescription());
		}
		return desc;
	}

	/**
	 * Récupère la progression pondérée de la tâche en pourcentage, basée sur les durées estimées des sous-tâches.
	 *
	 * @return La progression pondérée de la tâche en pourcentage.
	 */
	@Override
	public float getProgress() {
		// Calcul des progressions pondérées par les durées estimées des sous tâches
		float totalProgress = 0.0f;
		int totalEstimatedDate = getEstimatedDate();
		for (TaskComponent task : subTasks) {
			float taskProgress = task.getProgress();
			int taskEstimatedDate = task.getEstimatedDate();
			totalProgress += (taskProgress * taskEstimatedDate) / totalEstimatedDate;
		}
		return totalProgress;
	}


	/**
	 * Cette méthode effectue l'ajout d'une tache comme sous tache d'une autre tache
	 * @param priority a donner à un objet
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * Cette méthode effectue l'ajout de plusieurs tache comme sous taches d'une autre tache
	 * @param subTasks sous taches à ajouter dans la tache complexe
	 */
	public void addSubTasks(List<TaskComponent> subTasks) {
		this.subTasks = subTasks;
	}

	/**
	 * Cette methode nous permet de recupérer l'ensemble de nos sous taches
	 * @return subTasks sous taches composés
	 */
	public List<TaskComponent> getSubTasks() {
		return subTasks;
	}

	/**
	 * Cette méthode effectue l'ajout d'une tache comme sous tache d'une autre tache
	 * @param task ajouter comme sous tache
	 */
	public void addSubTask(TaskComponent task) {
		subTasks.add(task);
	}

	/**
	 * Cette méthode permet de supprimer dans une tache une sous tache
	 * @param task à supprimer de la tache complexe qui le contient
	 */
	public void removeSubTask(TaskComponent task) {
		subTasks.remove(task);
	}

	/**
	 *
	 * @param id l'index de tache à recupérer
	 * @return tache par les sous taches
	 */
	public TaskComponent getSubTask(int id) {
		return subTasks.get(id);
	}

	/**
	 * Accepte un visiteur et lui permet de visiter cette tâche.
	 *
	 * @param visitor Le visiteur à accepter.
	 */
	@Override
	public void accept(TaskVisitor visitor) {
		visitor.visit(this);
	}


}
