package commands.operations.filter;

import commands.CommandsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sheet.range.boundaries.api.Boundaries;
import sheet.range.boundaries.impl.BoundariesFactory;


public class FilterController {

    @FXML
    private ComboBox<String> comboBoxColumn1;

    @FXML
    private Button submitButton;

    @FXML
    private TextField textFieldRange;

    @FXML
    private TextField textFieldValues;

    private CommandsController mainController;

    public void setMainController(CommandsController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void columnAction(ActionEvent event) {

    }


    @FXML
    void columnOnMouseClicked(MouseEvent event) {
        String boundariesStr = textFieldRange.getText();
        Boundaries boundaries = BoundariesFactory.toBoundaries(boundariesStr);
        ObservableList<String> ranges = FXCollections.observableArrayList();
        for (int i = boundaries.getFrom().getCol(); i <= boundaries.getTo().getCol(); i++) {
            char character = (char) ('A' + i); // Compute the character
            String str = String.valueOf(character);
            ranges.add(str);
        }
        comboBoxColumn1.setItems(ranges);
    }

    @FXML
    void submitAction(ActionEvent event) {

    }

}
