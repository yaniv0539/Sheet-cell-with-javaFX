package commands.operations.filter;

import commands.CommandsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sheet.range.boundaries.api.Boundaries;
import sheet.range.boundaries.impl.BoundariesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class FilterController {

    @FXML
    private ComboBox<String> comboBoxColumn1;

    @FXML
    private Button buttonSubmit;

    @FXML
    private TextField textFieldFrom;

    @FXML
    private TextField textFieldSeparator;

    @FXML
    private TextField textFieldTo;


    @FXML
    private TextField textFieldValues;

    private CommandsController mainController;

    private Boundaries boundariesToFilter = null;
    private String filteringByColumn = null;

    public void setMainController(CommandsController mainController) {
        this.mainController = mainController;
    }

    public void init() {
        comboBoxColumn1.setDisable(true);
        textFieldValues.setDisable(true);
        buttonSubmit.setDisable(true);
        textFieldFrom.textProperty().addListener((observableValue, oldValue, newValue) -> handleRangeValueChange(newValue));
        textFieldTo.textProperty().addListener((observableValue, oldValue, newValue) -> handleRangeValueChange(newValue));
    }

    private void handleRangeValueChange(String newValue) {
        if (!setColumnOptions(textFieldFrom.getText() + textFieldSeparator.getText() + textFieldTo.getText())) {
            comboBoxColumn1.setDisable(true);
            textFieldValues.setDisable(true);
            buttonSubmit.setDisable(true);
        } else {
            comboBoxColumn1.setDisable(false);
            textFieldValues.setDisable(false);
        }
    }


    @FXML
    void columnAction(ActionEvent event) {
        if (comboBoxColumn1.getSelectionModel().getSelectedIndex() != -1) {
            buttonSubmit.setDisable(false);
        } else {
            buttonSubmit.setDisable(true);
        }
        filteringByColumn = comboBoxColumn1.getSelectionModel().getSelectedItem();
    }

    private boolean setColumnOptions(String rangeValue) {
        try {
            boundariesToFilter = BoundariesFactory.toBoundaries(rangeValue);

            if (!this.mainController.isBoundariesValidForCurrentSheet(boundariesToFilter)) {
                return false;
            }

            ObservableList<String> ranges = FXCollections.observableArrayList();
            for (int i = boundariesToFilter.getFrom().getCol(); i <= boundariesToFilter.getTo().getCol(); i++) {
                char character = (char) ('A' + i); // Compute the character
                String str = String.valueOf(character);
                ranges.add(str);
            }
            comboBoxColumn1.setItems(ranges);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    void submitAction(ActionEvent event) {
        String textFieldValuesText = textFieldValues.getText();
        List<String> filteringByValues = Stream.of(textFieldValuesText.split(",")).map(String::trim).toList();
        this.mainController.filterRange(boundariesToFilter, filteringByColumn, filteringByValues);
    }

}
