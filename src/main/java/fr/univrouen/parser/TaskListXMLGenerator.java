package fr.univrouen.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import taskManager.ComplexTask;
import taskManager.SimpleTask;
import taskManager.TaskComponent;
import tasklist.TaskList;

import java.io.FileOutputStream;

public class TaskListXMLGenerator {

    public void generateXML(TaskList taskList, String fileName) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);

            // Créer un gestionnaire d'événements pour générer le XML
            XMLHandler handler = new XMLHandler(outputStream);

            // Commencer la génération XML
            handler.startDocument();

            // Ajouter les tâches au fichier XML
            for (TaskComponent taskComponent : taskList.getAllTasks()) {
                writeTaskComponent(handler, taskComponent);
            }

            // Fin de la génération XML
            handler.endDocument();

            // Fermer le flux de sortie
            outputStream.close();

            System.out.println("Fichier XML généré avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeTaskComponent(XMLHandler handler, TaskComponent taskComponent) throws SAXException {
        if (taskComponent instanceof SimpleTask) {
            SimpleTask simpleTask = (SimpleTask) taskComponent;
            AttributesImpl attributes = new AttributesImpl();
            attributes.addAttribute("", "description", "description", "CDATA", simpleTask.getDescription());
            attributes.addAttribute("", "dueDate", "dueDate", "CDATA", simpleTask.getDueDate().toString());
            attributes.addAttribute("", "priority", "priority", "CDATA", simpleTask.getPriority().toString());
            attributes.addAttribute("", "estimatedDate", "estimatedDate", "CDATA",
                    simpleTask.getEstimatedDate().toString());
            attributes.addAttribute("", "progress", "progress", "CDATA", Float.toString(simpleTask.getProgress()));

            handler.startElement("", "simpleTask", "simpleTask", attributes);
            // handler.endElement("", "simpleTask", "simpleTask");
        } else if (taskComponent instanceof ComplexTask) {
            ComplexTask complexTask = (ComplexTask) taskComponent;
            AttributesImpl attributes = new AttributesImpl();
            attributes.addAttribute("", "priority", "priority", "CDATA", complexTask.getPriority().toString());

            handler.startElement("", "complexTask", "complexTask", attributes);
            for (TaskComponent subTask : complexTask.getSubTasks()) {
                writeTaskComponent(handler, subTask);
            }
            handler.endElement("", "complexTask", "complexTask");
        }
    }

    static class XMLHandler extends DefaultHandler {
        private FileOutputStream outputStream;

        public XMLHandler(FileOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        public void startDocument() throws SAXException {
            try {
                outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<tasklist>\n".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void endDocument() throws SAXException {
            try {
                outputStream.write("\n</tasklist>".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            try {
                outputStream.write("<".getBytes());
                outputStream.write(qName.getBytes());
                for (int i = 0; i < attributes.getLength(); i++) {
                    String attributeName = attributes.getQName(i);
                    String attributeValue = attributes.getValue(i);
                    outputStream.write(" ".getBytes());
                    outputStream.write(attributeName.getBytes());
                    outputStream.write("=\"".getBytes());
                    outputStream.write(attributeValue.getBytes());
                    outputStream.write("\"".getBytes());
                }
                if ("simpleTask".equals(qName)) {
                    outputStream.write("/>\n".getBytes());
                } else {
                    outputStream.write(">\n".getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            try {
                outputStream.write("</".getBytes());
                outputStream.write(qName.getBytes());
                outputStream.write(">".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}