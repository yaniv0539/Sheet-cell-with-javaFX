package commands.operations.sort;
import commands.CommandsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.range.boundaries.api.Boundaries;
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

    }

    @FXML
    void textFieldRangeKeyTyped(KeyEvent event) {

    }

    public void setMainController(CommandsController mainController){
        this.mainController = mainController;
    }

    public void init() {

    }

    //some more helper function


}
