package modelUI.impl;

import app.AppController;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sheet.coordinate.api.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class VersionDesignManager {

    int numberOfVersions;
    AppController mainController;
    Map<Integer,Map<Integer,TextFieldDesign>> cellsDesigns;
    Map<Integer,Map<Integer,Integer>> rowsLayouts;
    Map<Integer,Map<Integer,Integer>> columnsLayouts;

    public class VersionDesign {

        Map<Integer,TextFieldDesign> cellDesignsVersion;
        Map<Integer,Integer> rowsLayoutVersion;
        Map<Integer,Integer> columnsLayoutVersion;

        public VersionDesign(int Version) {
            cellDesignsVersion = cellsDesigns.get(Version);
            rowsLayoutVersion = rowsLayouts.get(Version);
            columnsLayoutVersion = columnsLayouts.get(Version);
        }

        public Map<Integer, Integer> getColumnsLayoutVersion() {
            return columnsLayoutVersion;
        }

        public Map<Integer, TextFieldDesign> getCellDesignsVersion() {
            return cellDesignsVersion;
        }

        public Map<Integer, Integer> getRowsLayoutVersion() {
            return rowsLayoutVersion;
        }
    }

    public VersionDesignManager() {
        numberOfVersions = 0;
        cellsDesigns = new HashMap<>();
        rowsLayouts = new HashMap<>();
        columnsLayouts = new HashMap<>();

    }

    public void addVersion(GridPane CurrGridPane){
        numberOfVersions++;
        setCellsDesign(CurrGridPane);
        setRowsLayouts(CurrGridPane);
        setColumnsLayouts(CurrGridPane);
    }

    private void setColumnsLayouts(GridPane gridPane) {
        Map<Integer,Integer> columnToWidth = new HashMap<>();
        for (int i = 0; i < gridPane.getColumnConstraints().size(); i++) {
            columnToWidth.put(i,(int) gridPane.getColumnConstraints().get(i).getPrefWidth());
        }
        columnsLayouts.put(numberOfVersions,columnToWidth);
    }

    private void setRowsLayouts(GridPane gridPane) {
        Map<Integer,Integer> rowToHeight = new HashMap<>();

        for (int i = 0; i < gridPane.getRowConstraints().size(); i++) {
            rowToHeight.put(i,(int) gridPane.getRowConstraints().get(i).getPrefHeight());
        }
        rowsLayouts.put(numberOfVersions,rowToHeight);
    }

    private void setCellsDesign(GridPane gridPane) {

        Map<Integer,TextFieldDesign> IndexNodeToCellDesign = new HashMap<>();
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            if (gridPane.getChildren().get(i) instanceof TextField tf) {

                IndexNodeToCellDesign.put(i,new TextFieldDesign(mainController.getBackground(tf),tf.getStyle(),tf.getFont()));
            }
        }

        cellsDesigns.put(numberOfVersions,IndexNodeToCellDesign);
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public VersionDesign getVersionDesign(int version){
        return new VersionDesign(version);
    }

}
