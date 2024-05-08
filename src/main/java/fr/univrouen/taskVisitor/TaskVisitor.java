package fr.univrouen.taskVisitor;

import fr.univrouen.task.*;

public interface TaskVisitor {
    void visit(SimpleTask task);
    void visit(ComplexTask task);
}
