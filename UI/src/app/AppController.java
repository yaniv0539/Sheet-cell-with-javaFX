package app;

import commands.CommandsController;
import header.HeaderController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import ranges.RangesController;
import sheet.SheetController;

public class AppController {

    @FXML private BorderPane appBorderPane;
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane commandsComponent;
    @FXML private CommandsController commandsComponentController;
    @FXML private ScrollPane rangesComponent;
    @FXML private RangesController rangesComponentController;
    private ScrollPane sheetComponent;
    private SheetController sheetComponentController;

    @FXML
    public void initialize() {
        if (headerComponentController != null && commandsComponentController != null && rangesComponentController != null) {
            headerComponentController.setMainController(this);
            commandsComponentController.setMainController(this);
            rangesComponentController.setMainController(this);

            sheetComponentController = new SheetController();
            sheetComponent = sheetComponentController.getInitializedSheet();
            appBorderPane.setCenter(sheetComponent);
        }
    }
}
