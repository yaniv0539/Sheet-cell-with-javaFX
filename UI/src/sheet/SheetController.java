package sheet;

import app.AppController;
import engine.api.Engine;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import modelUI.api.EffectiveValuesPoolPropertyReadOnly;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import sheet.layout.api.LayoutGetters;


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

    public ScrollPane getInitializedSheet(LayoutGetters layout) {
        //TODO:SPLIT INTO SMALLER FUNCTIONS, JUST FOR FLOW.

        setLayoutGridPane(layout);
        //until here set the grid.
        setBindsTo();
        setScrollPane();
        return scrollPane;
    }

    private void setLayoutGridPane(LayoutGetters layout) {

        // Create a GridPane
        gridPane.setGridLinesVisible(true);  // Enable grid lines for visualization

        //init the "dataBase" of effective values, text fields will bind to this.
        //cellsValue = new StringProperty[rows + 1][columns + 1];

        // Add column constraints to match FXML configuration
        for (int col = 0; col <= layout.getColumns(); col++) {

            ColumnConstraints columnConstraints = new ColumnConstraints();
            if (col == 0) {
                columnConstraints.setMinWidth(30);
                columnConstraints.setMaxWidth(30);
                columnConstraints.setPrefWidth(30);  // First column for row numbers
                columnConstraints.setHgrow(Priority.NEVER);  // No horizontal grow
            } else {
                columnConstraints.setMinWidth(layout.getSize().getWidth());
                columnConstraints.setPrefWidth(layout.getSize().getWidth());  // Other columns
                columnConstraints.setHgrow(Priority.NEVER);  // Allow horizontal grow
            }
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        // Add row constraints to match FXML configuration
        for (int row = 0; row <= layout.getRows(); row++) {
            RowConstraints rowConstraints = new RowConstraints();
            if (row == 0) {
                rowConstraints.setMinHeight(30);
                rowConstraints.setMaxHeight(30);
                rowConstraints.setPrefHeight(30);
                rowConstraints.setVgrow(Priority.NEVER);  // No horizontal grow
            } else {
                rowConstraints.setMinHeight(layout.getSize().getHeight());
                rowConstraints.setPrefHeight(layout.getSize().getHeight());  // Set preferred height for rows
                rowConstraints.setVgrow(Priority.NEVER);  // Allow vertical grow
            }
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }

    private void setBindsTo() {

        EffectiveValuesPoolPropertyReadOnly dataToView = mainController.getEffectiveValuesPool();
        for (int row = 0; row < gridPane.getRowCount(); row++) {
            for (int col = 0; col < gridPane.getColumnCount(); col++) {

                TextField textField = new TextField();
                textField.setEditable(false);  // Disable editing

                textField.setMaxWidth(Double.MAX_VALUE);  // Allow TextField to stretch horizontally
                textField.setMaxHeight(Double.MAX_VALUE);  // Allow TextField to stretch vertically
                //textField.getSt("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-radius: 5px;");
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

    private TextField getTextFieldFromGrid(GridPane gridPane, int row, int column) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return (TextField) node;
            }
        }
        return null; // Return null if no TextField is found
    }

    public void changeColorDependedCoordinate(ListChangeListener.Change<? extends Coordinate> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                // Do something with the added items
                for (Coordinate coordinate : change.getAddedSubList()) {
                    Objects.requireNonNull(this.targetTextField(coordinate)).setStyle("-fx-background-color: lightblue;");
                }
            }

            if (change.wasRemoved()) {
                // Do something with the removed items
                for (Coordinate coordinate : change.getRemoved()) {
                    Objects.requireNonNull(targetTextField(coordinate)).setStyle("");
                }
            }
        }
    }

    public void changeColorInfluenceCoordinate(ListChangeListener.Change<? extends Coordinate> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                // Do something with the added items
                for (Coordinate coordinate : change.getAddedSubList()) {
                    Objects.requireNonNull(targetTextField(coordinate)).setStyle("-fx-background-color: lightgreen;");
                }
            }

            if (change.wasRemoved()) {
                // Do something with the removed items
                for (Coordinate coordinate : change.getRemoved()) {
                    Objects.requireNonNull(targetTextField(coordinate)).setStyle("");
                }
            }
        }
    }

    private TextField targetTextField(Coordinate coordinate) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if(node instanceof TextField &&
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == coordinate.getRow() + 1 &&
                    GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == coordinate.getCol() + 1)
            {
                return (TextField) node;  // Cast to TextField and return it
            }
        }
        return null;
    }
}



