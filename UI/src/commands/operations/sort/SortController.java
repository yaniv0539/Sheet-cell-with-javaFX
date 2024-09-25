package commands.operations.sort;
import commands.CommandsController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.range.boundaries.api.Boundaries;
import sheet.range.boundaries.impl.BoundariesFactory;
import sheet.range.boundaries.impl.BoundariesImpl;

import java.util.List;

public class SortController {

    @FXML
    private HBox HBoxColumnsCheckBox;

    @FXML
    private Button buttonSort;

    @FXML
    private Label labelRange;

    @FXML
    private Label labelSelectColumn;

    @FXML
    private TextField textFieldRange;

    private CommandsController mainController;
    Tooltip validationTooltip = new Tooltip("Input must be a range in this format:\n" +
            "<top left cell coordinate>..<bottom right cell coordinate>");

    public void init() {
        buttonSort.setDisable(true);
        labelSelectColumn.setDisable(true);
        labelRange.setDisable(true);
        // Create a Tooltip for validation message

        // Initially hide the Tooltip
        validationTooltip.setAutoHide(false);
        Tooltip.install(textFieldRange, validationTooltip);
        validationTooltip.hide();

        textFieldRange.textProperty().addListener((observable, oldValue, newValue) -> handleChangeTextRange(newValue));
    }

    private void handleChangeTextRange(String newValue) {
        if (!isInputValid(newValue)) {
            // Show tooltip if the input is invalid
            validationTooltip.show(textFieldRange, textFieldRange.getScene().getWindow().getX() + textFieldRange.getLayoutX() + 10,
                    textFieldRange.getScene().getWindow().getY() + textFieldRange.getLayoutY() + textFieldRange.getHeight() + 30);
            textFieldRange.setStyle("-fx-border-color: red;");
            // Disable Enter key press
            textFieldRange.addEventFilter(KeyEvent.KEY_PRESSED, this::disableEnterKey);
        } else {
            // Hide tooltip if the input is valid
            validationTooltip.hide();
            textFieldRange.setStyle("-fx-border-color: lightblue;"); // Reset style
            // Enable Enter key press
            textFieldRange.removeEventFilter(KeyEvent.KEY_PRESSED, this::disableEnterKey);
        }
    }

    private boolean isInputValid(String newValue) {
        return (BoundariesFactory.isValidBoundariesFormat(newValue) &&
                mainController.isBoundariesValidForCurrentSheet(BoundariesFactory.toBoundaries(newValue)));
    }

    @FXML
    void buttonSortAction(ActionEvent event) {
        //hardcoded test
        Coordinate from = CoordinateFactory.createCoordinate(2,1);
        Coordinate to = CoordinateFactory.createCoordinate(5,4);
        Boundaries b =  BoundariesImpl.create(from,to);
        List<String> columToSort = List.of("C");

        mainController.sortRange(b,columToSort);
    }

    @FXML
    void textFieldRangeAction(ActionEvent event) {
        //to add check box to the hbox.
    }

    @FXML
    void textFieldRangeKeyTyped(KeyEvent event) {

    }

    public void setMainController(CommandsController mainController){
        this.mainController = mainController;
    }



    //some more helper function
    private void disableEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume();  // This will prevent the default action when Enter is pressed
        }
    }




}
