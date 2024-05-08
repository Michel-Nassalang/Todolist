package fr.univrouen.visitor;

import fr.univrouen.task.*;
import org.xml.sax.SAXException;

public interface TaskVisitor {
    void visit(SimpleTask task);
    void visit(ComplexTask task);
}
