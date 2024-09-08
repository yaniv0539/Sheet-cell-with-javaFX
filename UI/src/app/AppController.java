package app;

import commands.CommandsController;
import header.HeaderController;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import modelUI.impl.FocusCellImpl;
import ranges.RangesController;
import sheet.SheetController;

import java.util.Random;

public class AppController {

    @FXML private BorderPane appBorderPane;
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane commandsComponent;
    @FXML private CommandsController commandsComponentController;
    @FXML private ScrollPane rangesComponent;
    @FXML private RangesController rangesComponentController;
    private FocusCellImpl cellInFocus;
    private ScrollPane sheetComponent;
    private SheetController sheetComponentController;

    @FXML
    public void initialize() {
        cellInFocus = new FocusCellImpl();

        if (headerComponentController != null && commandsComponentController != null && rangesComponentController != null) {
            headerComponentController.setMainController(this);
            commandsComponentController.setMainController(this);
            rangesComponentController.setMainController(this);
//            //test
//            headerComponentController.bindCellIdTextField(cellInFocus.coordinate);
            //dynamic sheet component
            sheetComponentController = new SheetController();
            sheetComponent = sheetComponentController.getInitializedSheet();
            appBorderPane.setCenter(sheetComponent);
        }
    }


    public void focusChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
    {
        Random random = new Random(); //test
        if(newValue)
        {
            //change text box cell id
            //change orignal value
            //change version
            //all this changes should be in currentCellInFocus
//            cellInFocus.setCoordinate("A" +random.nextInt(26) + 1); //test
        }
    }



}
