package commands;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class commandsController {

    @FXML
    private Button bottonSetColumnAlignment;

    @FXML
    private Button buttonFilter;

    @FXML
    private Button buttonSetCellDesign;

    @FXML
    private Button buttonSetColumnsSize;

    @FXML
    private Button buttonSort;

    @FXML
    void filterAction(ActionEvent event) {
        buttonFilter.setDisable(true);
    }

    @FXML
    void setCellDesignAction(ActionEvent event) {

    }

    @FXML
    void setColumnAlignmentAction(ActionEvent event) {

    }

    @FXML
    void setColumnsSizeAction(ActionEvent event) {

    }

    @FXML
    void sortAction(ActionEvent event) {

    }

}
