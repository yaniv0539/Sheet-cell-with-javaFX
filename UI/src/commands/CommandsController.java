package commands;

import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CommandsController {

    private AppController mainController;

    @FXML
    private Button bottonSetColumnAlignment;

    @FXML
    private Button buttonChangeColumnsSize12;

    @FXML
    private Button buttonChangeColumnsSize13;

    @FXML
    private Button buttonSetCellDesign;

    @FXML
    private Button buttonSetColumnsSize;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void handleChangeColumnsSize(ActionEvent event) {

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

}
