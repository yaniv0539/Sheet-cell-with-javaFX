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
import modelUI.api.EffectiveValuesPoolPropertyReadOnly;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;

import java.util.ArrayList;
import java.util.List;

public class SheetController {

    private AppController mainController;

    private ScrollPane scrollPane;
    public GridPane gridPane;

    public SheetController() {
        scrollPane = new ScrollPane();
        gridPane = new GridPane();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public ScrollPane getInitializedSheet(int rows, int columns) {
        //TODO:SPLIT INTO SMALLER FUNCTIONS, JUST FOR FLOW.

        setLayoutGridPane(rows,columns);
        //until here set the grid.
        setBindsTo();
        setScrollPane();
        return scrollPane;
    }

    private void setLayoutGridPane(int rows, int columns) {

        // Create a GridPane
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
    }

    private void setBindsTo() {

        EffectiveValuesPoolPropertyReadOnly dataToView = mainController.getEffectiveValuesPool();
        //הורדתי את השווה בלולאות אם יהיה באג לבדוק את זה
        for (int row = 0; row < gridPane.getRowCount(); row++) {
            for (int col = 0; col < gridPane.getColumnCount(); col++) {

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
                    if (row != 0 || col != 0) {
                        Coordinate coordinate = CoordinateFactory.createCoordinate(row-1,col-1);
                        textField.textProperty().bind(dataToView.getEffectiveValuePropertyAt(coordinate));
                        //add listener to focus, need to change.
                        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                            mainController.focusChanged(newValue, coordinate);
                        });
                    }
                }
                // Add TextField to the GridPane
                gridPane.add(textField, col, row); //gridPane is public just for flow.

                // Set alignment for grid children if necessary
                GridPane.setHgrow(textField, Priority.ALWAYS);
                GridPane.setVgrow(textField, Priority.ALWAYS);
                GridPane.setHalignment(textField, HPos.CENTER);
                GridPane.setValignment(textField, VPos.CENTER);
            }
        }
    }

    private void setScrollPane() {
        scrollPane.setFitToWidth(true);  // Ensure it stretches to fit the width
        scrollPane.setFitToHeight(true);  // Ensure it stretches to fit the height
        scrollPane.setContent(gridPane);
    }

}


