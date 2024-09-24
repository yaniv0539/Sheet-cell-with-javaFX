package commands.operations.sort;
import commands.CommandsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

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
