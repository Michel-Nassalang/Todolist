package fr.univrouen.parser;

import fr.univrouen.visitor.TaskAttributeWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import fr.univrouen.task.*;
import fr.univrouen.tasklistObserver.TaskList;

import java.io.FileOutputStream;

public class TaskListXMLGenerator {

    public boolean generateXML(TaskList taskList, String fileName) {
        boolean result = true;
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

            //Fichier XML généré avec succès.
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private static void writeTaskComponent(XMLHandler handler, TaskComponent taskComponent) throws SAXException {
        TaskAttributeWriter taskAttributeWriter = new TaskAttributeWriter(handler);
        taskComponent.accept(taskAttributeWriter);
    }

    public static class XMLHandler extends DefaultHandler {
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