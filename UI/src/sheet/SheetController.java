package sheet;

import app.AppController;
import engine.api.Engine;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import sheet.cell.api.CellGetters;

import java.util.ArrayList;
import java.util.List;

public class SheetController {

    private AppController mainController;

    private ScrollPane scrollPane;
    public GridPane gridPane;
//    private List<List<TextField>> cells; //no need
//    private StringProperty[][] cellsValue; //should be here or in app controller ?

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public ScrollPane getInitializedSheet(int rows, int columns) {
        //TODO:SPLIT INTO SMALLER FUNCTIONS, JUST FOR FLOW.

        // Create a ScrollPane to contain the dynamic GridPane
        this.scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);  // Ensure it stretches to fit the width
        scrollPane.setFitToHeight(true);  // Ensure it stretches to fit the height

        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);  // Enable grid lines for visualization

        //init the "dataBase" of effective values, text fields will bind to this.
        //cellsValue = new StringProperty[rows + 1][columns + 1];

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
        //until here set the grid.

        // Set the GridPane as the content of the ScrollPane
        scrollPane.setContent(gridPane);

        return scrollPane;
    }
}


