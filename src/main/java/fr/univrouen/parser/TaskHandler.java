package fr.univrouen.parser;
import fr.univrouen.taskVisitor.TaskAttributePopulator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.univrouen.task.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est un gestionnaire d'événements SAX qui analyse un fichier XML de tâches
 * et crée des objets TaskComponent correspondants.
 */
public class TaskHandler extends DefaultHandler {
    private List<TaskComponent> tasks;
    private TaskComponent currentTask;
    private StringBuilder currentValue;
    private boolean
     inSimpleTask;
    private int inComplexTask;
    private List<ComplexTask>  contentTasks;

    /**
     * Constructeur par défaut qui initialise les listes de tâches et de tâches complexes.
     */
    public TaskHandler() {
        tasks = new ArrayList<>();
        contentTasks = new ArrayList<>();
        inComplexTask = -1;
    }

    /**
     * Renvoie la liste des tâches analysées à partir du fichier XML.
     *
     * @return La liste des tâches analysées.
     */
    public List<TaskComponent> getTasks() {
        return tasks;
    }

    /**
     * Initialise un nouvel objet TaskComponent en fonction de l'élément XML rencontré.
     * Cette méthode est appelée lorsqu'un nouvel élément XML est rencontré.
     *
     * @param uri        L'URI de l'espace de noms XML.
     * @param localName  Le nom local (sans préfixe) de l'élément.
     * @param qName      Le nom qualifié (avec préfixe) de l'élément.
     * @param attributes Les attributs de l'élément.
     * @throws SAXException En cas d'erreur lors de l'analyse SAX.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "simpleTask":
                if (inComplexTask > -1) {
                    SimpleTask simpleTask = new SimpleTask();
                    populateTaskFromAttributes(simpleTask, attributes);
                    contentTasks.get(inComplexTask).addSubTask(simpleTask);
                } else {
                    currentTask = new SimpleTask();
                    populateTaskFromAttributes(currentTask, attributes);
                    tasks.add(currentTask);
                }
                inSimpleTask = true;
                break;
            case "complexTask":
                currentTask = new ComplexTask();
                populateTaskFromAttributes(currentTask, attributes);
                contentTasks.add((ComplexTask) currentTask);
                inSimpleTask = false;
                inComplexTask +=1;
                break;
            default:
                currentValue = new StringBuilder();
                break;
        }
    }

    /**
     * Gère la fin d'un élément XML en fonction de son nom qualifié (qName).
     * Cette méthode est appelée lorsqu'un élément XML se termine.
     *
     * @param uri       L'URI de l'espace de noms XML.
     * @param localName Le nom local (sans préfixe) de l'élément.
     * @param qName     Le nom qualifié (avec préfixe) de l'élément.
     * @throws SAXException En cas d'erreur lors de l'analyse SAX.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "description":
                if (inSimpleTask) {
                    ((SimpleTask) currentTask).setDescription(currentValue.toString());
                }
                break;
            case "dueDate":
                if (inSimpleTask) {
                    ((SimpleTask) currentTask).setDueDate(LocalDate.parse(currentValue.toString()));
                }
                break;
            case "priority":
                if (inSimpleTask) {
                    ((SimpleTask) currentTask).setPriority(Priority.valueOf(currentValue.toString().toUpperCase()));
                } else if (inComplexTask != -1) {
                    ((ComplexTask) currentTask).setPriority(Priority.valueOf(currentValue.toString().toUpperCase()));
                }
                break;
            case "estimatedDate":
                if (inSimpleTask) {
                    ((SimpleTask) currentTask).setEstimatedDate(Integer.parseInt(currentValue.toString()));
                }
                break;
            case "progress":
                if (inSimpleTask) {
                    ((SimpleTask) currentTask).setProgress(Float.parseFloat(currentValue.toString()));
                }
                break;
                case "complexTask":
                ComplexTask c  = contentTasks.get(inComplexTask);
                if (inComplexTask > 0) {
                    contentTasks.get(inComplexTask - 1).addSubTask(c);
                }else{
                    tasks.add(c);
                }
                contentTasks.remove(c);
                inComplexTask--;
                break;
        }
    }

    /**
     * Gère les caractères d'un élément XML.
     * Cette méthode est appelée lorsque des caractères sont rencontrés entre les balises XML.
     *
     * @param ch     Un tableau de caractères contenant les données.
     * @param start  L'indice de départ dans le tableau de caractères.
     * @param length Le nombre de caractères à utiliser à partir de l'indice de départ.
     * @throws SAXException En cas d'erreur lors de l'analyse SAX.
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue.append(ch, start, length);
    }

    /**
     * Initialise les attributs de la tâche en cours à partir des attributs XML.
     *
     * @param task       La tâche à initialiser.
     * @param attributes Les attributs XML contenant les données à utiliser pour initialiser la tâche.
     */
    private void populateTaskFromAttributes(TaskComponent task, Attributes attributes) {
        TaskAttributePopulator attributePopulator = new TaskAttributePopulator(attributes);
        task.accept(attributePopulator);
    }
}
