package fr.univrouen.task;

import fr.univrouen.visitor.TaskVisitor;

import java.time.LocalDate;

public interface TaskComponent {
	
	LocalDate getDueDate(); // Date d'échéance

	Priority getPriority(); // Priorités possibles : urgent, normal ou secondaire ;
	
	Integer getEstimatedDate(); // Durée estimée de la tache en nombre de jours
	
	String getDescription(); // Description court de la tache
	
	float getProgress(); // Progression en pourcentage

	void accept(TaskVisitor visitor); //acces au visitor
}
