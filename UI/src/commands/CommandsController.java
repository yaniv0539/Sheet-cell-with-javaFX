package commands;

import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CommandsController {

    private static final String SET_COLUMN_SIZE_FXML_INCLUDE_RESOURCE = "visual/column/size/columnSize.fxml";

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
    void setColumnsSizeAction(ActionEvent event) throws IOException {
        activateCommandAction(SET_COLUMN_SIZE_FXML_INCLUDE_RESOURCE, "Set Column Size");
    }

    void activateCommandAction(String resource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(resource);
        fxmlLoader.setLocation(url);
        Parent popupRoot = fxmlLoader.load(url.openStream());

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Scene popupScene = new Scene(popupRoot, 360, 120);
        popupStage.setResizable(false);
        popupStage.setScene(popupScene);

        popupStage.showAndWait();
    }

}
