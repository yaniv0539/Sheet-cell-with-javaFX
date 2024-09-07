package ranges;

import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class RangesController {

    @FXML
    private Button bottonSetColumnAlignment;

    @FXML
    private Button buttonSetCellDesign;

    @FXML
    private Button buttonSetColumnsSize;

    @FXML
    private Button buttonSort;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
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
