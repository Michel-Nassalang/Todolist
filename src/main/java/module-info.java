/**
 * Ce module gère toutes les bibliothèques nécessaires à notre projet Todolist
 */
module fr.univrouen.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    exports fr.univrouen.todolist;
    exports fr.univrouen.analyzer;
    exports fr.univrouen.editor;
    exports fr.univrouen.task;
    exports fr.univrouen.parser;
    exports fr.univrouen.tasklistFactory;
    exports fr.univrouen.tasklistObserver;
    exports fr.univrouen.taskVisitor;
    exports fr.univrouen.ui;
}
