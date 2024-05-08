package fr.univrouen.analyzer;

import fr.univrouen.task.TaskComponent;
import fr.univrouen.tasklistObserver.TaskList;

import java.util.List;
import java.util.Scanner;

public class Tsktop {
    public static void printask(TaskComponent task){
         System.out.println(
                 "Description: " + task.getDescription() +
                         ", Date d'échéance: " + task.getDueDate() +
                         ", Progression: " + task.getProgress()* 100 + "%" +
                         ", Durée d'estimation: " + task.getEstimatedDate() +
                         ", Priorité: " + task.getPriority()
         );
    }
    public static void main(String[] args) {
        TaskAnalyzer taskAnalyzer = TaskAnalyzer.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Charger une liste de tâches à partir d'un fichier");
            System.out.println("2. Afficher les 5 premières tâches incomplètes");
            System.out.println("3. Ordonner les tâches par date d'échéance");
            System.out.println("4. Quitter");

            System.out.print("Choix: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Entrez le chemin du fichier: ");
                    String filename = scanner.nextLine();
                    TaskList tasklist = taskAnalyzer.loadFromFile(filename);
                    if(tasklist != null){
                        System.out.println("------Liste de tâches chargée avec succès !------");
                    }else {
                        System.out.println("------Veuillez revoir vos données entrées------");
                    }
                    break;
                case 2:
                    System.out.println("Les 5 premières tâches incomplètes sont :");
                    List<TaskComponent> incompleteTasks = taskAnalyzer.getTop5IncompleteTasks();
                    if (incompleteTasks != null && !incompleteTasks.isEmpty()) {
                        for (TaskComponent task : incompleteTasks) {
                            printask(task);
                        }
                    } else {
                        System.out.println("------Aucune tâche incomplète n'a été trouvée.------");
                    }
                    break;
                case 3:
                    System.out.println("Les tâches ordonnées par date d'échéance sont :");
                    List<TaskComponent> orderedTasks = taskAnalyzer.orderTaskList();
                    if (orderedTasks != null && !orderedTasks.isEmpty()) {
                        for (TaskComponent task : orderedTasks) {
                            printask(task);
                        }
                    } else {
                        System.out.println("------Aucune tâche n'a été trouvée.------");
                    }
                    break;
                case 4:
                    System.out.println("------Au revoir !------");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("------Choix invalide !------");
            }
        }
    }
}
