package sheet;

import app.AppController;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class SheetController {

    private AppController mainController;

    private ScrollPane scrollPane;
    private GridPane gridPane;
    private List<List<TextField>> cells;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public ScrollPane getInitializedSheet() {

        // Create a ScrollPane to contain the dynamic GridPane
        this.scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);  // Ensure it stretches to fit the width
        scrollPane.setFitToHeight(true);  // Ensure it stretches to fit the height

        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);  // Enable grid lines for visualization

        // Define number of rows and columns
        int rows = 20;
        int columns = 20;

        // Add column constraints to match FXML configuration
        for (int col = 0; col <= columns; col++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            if (col == 0) {
                columnConstraints.setMinWidth(30);
                columnConstraints.setMaxWidth(30);
                columnConstraints.setPrefWidth(30);  // First column for row numbers
                columnConstraints.setHgrow(Priority.NEVER);  // No horizontal grow
            } else {
                columnConstraints.setMinWidth(70);
                columnConstraints.setPrefWidth(70);  // Other columns
                columnConstraints.setHgrow(Priority.SOMETIMES);  // Allow horizontal grow
            }
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        // Add row constraints to match FXML configuration
        for (int row = 0; row <= rows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            if (row == 0) {
                rowConstraints.setMinHeight(30);
                rowConstraints.setMaxHeight(30);
                rowConstraints.setPrefHeight(30);
                rowConstraints.setVgrow(Priority.NEVER);  // No horizontal grow
            } else {
                rowConstraints.setMinHeight(30);
                rowConstraints.setPrefHeight(30);  // Set preferred height for rows
                rowConstraints.setVgrow(Priority.SOMETIMES);  // Allow vertical grow
            }
            gridPane.getRowConstraints().add(rowConstraints);
        }

        cells = new ArrayList<>(rows + 1);

        // Dynamically populate the GridPane with TextFields
        for (int row = 0; row <= rows; row++) {
            cells.add(new ArrayList<>(columns + 1));
            for (int col = 0; col <= columns; col++) {
                TextField textField = new TextField();
                textField.setEditable(false);  // Disable editing

                textField.setMaxWidth(Double.MAX_VALUE);  // Allow TextField to stretch horizontally
                textField.setMaxHeight(Double.MAX_VALUE);  // Allow TextField to stretch vertically

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

                // Add TextField to cells
                cells.get(row).add(textField);

                // Add TextField to the GridPane
                gridPane.add(textField, col, row);

                // Set alignment for grid children if necessary
                GridPane.setHgrow(textField, Priority.ALWAYS);
                GridPane.setVgrow(textField, Priority.ALWAYS);
                GridPane.setHalignment(textField, HPos.CENTER);
                GridPane.setValignment(textField, VPos.CENTER);
            }
        }

        // Set the GridPane as the content of the ScrollPane
        scrollPane.setContent(gridPane);

        return scrollPane;
    }

}
