package fr.univrouen.ui;

public class AlertUI {
    public static void showAlert(javafx.scene.control.Alert.AlertType alertType, String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
