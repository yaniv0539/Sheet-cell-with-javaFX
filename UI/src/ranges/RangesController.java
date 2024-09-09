package ranges;

import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RangesController {

    private static final String ADD_RANGE_POPUP_FXML_INCLUDE_RESOURCE = "operations/add/addRange.fxml";
    private static final String DELETE_RANGE_POPUP_FXML_INCLUDE_RESOURCE = "operations/delete/deleteRange.fxml";

    @FXML
    private Button buttonAddRange;

    @FXML
    private Button buttonDeleteRange;

    @FXML
    private Button buttonShowRange;

    @FXML
    private TextArea rangeList;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void addRangeAction(ActionEvent event) throws IOException {
        activateRangeAction(ADD_RANGE_POPUP_FXML_INCLUDE_RESOURCE, "Add Range");
    }

    @FXML
    void deleteRangeAction(ActionEvent event) throws IOException {
        activateRangeAction(DELETE_RANGE_POPUP_FXML_INCLUDE_RESOURCE, "Delete Range");
    }

    @FXML
    void showRangeAction(ActionEvent event) throws IOException {
        // TODO: check if a range had been selected, if not throw exception. Else, color the selected range on board.
    }

    void activateRangeAction(String resource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(resource);
        fxmlLoader.setLocation(url);
        Parent popupRoot = fxmlLoader.load(url.openStream());

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Scene popupScene = new Scene(popupRoot, 415, 100);
        popupStage.setResizable(false);
        popupStage.setScene(popupScene);

        popupStage.showAndWait();
    }

}
