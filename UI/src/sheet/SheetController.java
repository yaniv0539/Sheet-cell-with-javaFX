package sheet;

import app.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;

public class SheetController {

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public ScrollPane getInitializedSheet() {

        // Create a ScrollPane to contain the dynamic GridPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);  // Ensure it stretches to fit the width
        scrollPane.setFitToHeight(true);  // Ensure it stretches to fit the height

        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 300);  // Minimum size of the grid
        gridPane.setPrefSize(600, 400); // Preferred size
        gridPane.setGridLinesVisible(true);  // Enable grid lines for visualization

        // Define number of rows and columns
        int rows = 8;
        int columns = 6;

        // Add column constraints to match FXML configuration
        for (int col = 0; col <= columns; col++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            if (col == 0) {
                columnConstraints.setPrefWidth(30);  // First column for row numbers
                columnConstraints.setHgrow(Priority.NEVER);  // No horizontal grow
            } else {
                columnConstraints.setPrefWidth(100);  // Other columns
                columnConstraints.setHgrow(Priority.SOMETIMES);  // Allow horizontal grow
            }
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        // Add row constraints to match FXML configuration
        for (int row = 0; row <= rows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30);  // Set preferred height for rows
            rowConstraints.setVgrow(Priority.SOMETIMES);  // Allow vertical grow
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // Dynamically populate the GridPane with TextFields
        for (int row = 0; row <= rows; row++) {
            for (int col = 0; col <= columns; col++) {
                TextField textField = new TextField();
                textField.setEditable(false);  // Disable editing
                textField.setPrefSize(154, 30);  // Set preferred size

                // Set font size and alignment to match FXML
                textField.setFont(Font.font("System", 12));
                textField.setAlignment(javafx.geometry.Pos.CENTER);

                // Dynamically set the content of the TextField
                if (row == 0 && col > 0) {
                    textField.setText(Character.toString((char) ('A' + col - 1)));  // Column headers (A, B, C, etc.)
                } else if (col == 0 && row > 0) {
                    textField.setText(Integer.toString(row));  // Row headers (1, 2, 3, etc.)
                } else {
                    textField.setText("");  // Empty cells
                }

                // Add TextField to the GridPane
                gridPane.add(textField, col, row);

                // Set alignment for grid children if necessary
                GridPane.setHalignment(textField, HPos.CENTER);
                GridPane.setValignment(textField, VPos.CENTER);
            }
        }

        // Set the GridPane as the content of the ScrollPane
        scrollPane.setContent(gridPane);

        return scrollPane;
    }

}
