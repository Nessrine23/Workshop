module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;  // Required for javafx application
    requires java.sql;
    requires mysql.connector.j;  // Add this line to access java.sql module

    opens tn.edu.esprit.gui to javafx.fxml;  // Allow reflection by JavaFX
    exports tn.edu.esprit.gui;  // Make the package accessible

    exports tn.edu.esprit.entites;  // If you want to export the Personne class
}
