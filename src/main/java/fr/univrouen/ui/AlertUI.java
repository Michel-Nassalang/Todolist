package fr.univrouen.ui;

/**
 * La classe AlertUI fournit une méthode statique pour afficher des alertes dans l'interface utilisateur JavaFX.
 */
public class AlertUI {
    /**
     * Affiche une alerte avec le type spécifié, le titre et le contenu.
     *
     * @param alertType le type de l'alerte (par exemple : INFORMATION, ERREUR, AVERTISSEMENT, CONFIRMATION).
     * @param title     le titre de l'alerte.
     * @param content   le contenu de l'alerte.
     */
    public static void showAlert(javafx.scene.control.Alert.AlertType alertType, String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
