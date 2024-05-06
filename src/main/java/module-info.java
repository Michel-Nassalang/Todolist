module fr.univrouen.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens fr.univrouen.todolist to javafx.fxml;
    exports fr.univrouen.todolist;
}