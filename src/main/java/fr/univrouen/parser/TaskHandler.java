package fr.univrouen.parser;
import fr.univrouen.taskVisitor.TaskAttributePopulator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.univrouen.task.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskHandler extends DefaultHandler {
    private List<TaskComponent> tasks;
    private TaskComponent currentTask;
    private StringBuilder currentValue;
    private boolean
     inSimpleTask;
    private int inComplexTask;
    private List<ComplexTask>  contentTasks;

    public TaskHandler() {
        tasks = new ArrayList<>();
        contentTasks = new ArrayList<>();
        inComplexTask = -1;
    }

    public List<TaskComponent> getTasks() {
        return tasks;
    }

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

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue.append(ch, start, length);
    }

    private void populateTaskFromAttributes(TaskComponent task, Attributes attributes) {
        TaskAttributePopulator attributePopulator = new TaskAttributePopulator(attributes);
        task.accept(attributePopulator);
    }
}
