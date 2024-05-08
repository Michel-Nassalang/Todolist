package fr.univrouen.task;

import fr.univrouen.visitor.TaskVisitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComplexTask implements TaskComponent {

	// taches formants la tâche complexe et priorité donnée par l'utilisateur
	protected List<TaskComponent> subTasks = new ArrayList<TaskComponent>();
	private Priority priority;

	public ComplexTask() {
	}

	public ComplexTask(List<TaskComponent> subTasks, Priority priority) {
		this.subTasks = subTasks;
		this.priority = priority;
	}

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

	@Override
	public Priority getPriority() {
		return priority;
	}

	@Override
	public Integer getEstimatedDate() {
		Integer totalestimdate = 0;
		// Somme des durées estimées
		for (TaskComponent task : subTasks) {
			totalestimdate += task.getEstimatedDate();
		}
		return totalestimdate;
	}

	@Override
	public String getDescription() {
		String desc = "";
		// Sommation des descriptions des sous taches
		for (TaskComponent task : subTasks) {
			desc += (" " + task.getDescription());
		}
		return desc;
	}

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


	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void addSubTasks(List<TaskComponent> subTasks) {
		this.subTasks = subTasks;
	}

	public List<TaskComponent> getSubTasks() {
		return subTasks;
	}

	public void addSubTask(TaskComponent task) {
		subTasks.add(task);
	}

	public void removeSubTask(TaskComponent task) {
		subTasks.remove(task);
	}

	public TaskComponent getSubTask(int id) {
		return subTasks.get(id);
	}

	@Override
	public void accept(TaskVisitor visitor) {
		visitor.visit(this);
	}


}
