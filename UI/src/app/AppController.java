package app;

import commands.CommandsController;
import engine.api.Engine;
import engine.impl.EngineImpl;
import header.HeaderController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelUI.api.FocusCell;
import modelUI.impl.FocusCellImpl;
import ranges.RangesController;
import sheet.SheetController;
import sheet.cell.api.CellGetters;
import sheet.coordinate.impl.CoordinateFactory;

import java.io.File;

public class AppController {

    @FXML private BorderPane appBorderPane;
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane commandsComponent;
    @FXML private CommandsController commandsComponentController;
    @FXML private ScrollPane rangesComponent;
    @FXML private RangesController rangesComponentController;

    private SimpleBooleanProperty isFileSelected;
    private SimpleStringProperty selectedFileProperty;

    private ScrollPane sheetComponent;
    private SheetController sheetComponentController;
    private Stage primaryStage;
    private FocusCell cellInFocus;
    private StringProperty[][] cellsValue; //should be here or in app controller ?
    private Engine engine;

    public AppController() {
        this.isFileSelected = new SimpleBooleanProperty(false);
        this.selectedFileProperty = new SimpleStringProperty("");
        this.cellInFocus = new FocusCellImpl();
    }

    @FXML
    public void initialize() {


        engine = EngineImpl.create();

        if (headerComponentController != null && commandsComponentController != null && rangesComponentController != null) {
            headerComponentController.setMainController(this);
            commandsComponentController.setMainController(this);
            rangesComponentController.setMainController(this);

//            headerComponentController.getTextFieldCellId.textProperty().bind(cellInFocus.coordinate);
//            headerComponentController.textFieldOrignalValue.textProperty().bind(cellInFocus.originalValue);
//            headerComponentController.textFieldLastUpdateInVersion.textProperty().bind(cellInFocus.lastUpdateVersion);
//            headerComponentController.buttonUpdateCell.disableProperty().bind(isFileSelected.not());
//            headerComponentController.splitMenuButtonSelectVersion.disableProperty().bind(isFileSelected.not());
//            headerComponentController.buttonUpdateCell.disableProperty().bind(isFileSelected.not());
//            headerComponentController.buttonUpdateCell.disableProperty().bind(isFileSelected.not());

        }

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void uploadXml()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select words file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        engine.readXMLInitFile(absolutePath);
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);

        //TODO: from here put in private function
        sheetComponentController = new SheetController();
        sheetComponent = sheetComponentController.getInitializedSheet(engine.getSheetStatus().getLayout().getRows(),
                                                                      engine.getSheetStatus().getLayout().getColumns()); //only for structure of flow.
        setContentAndBindsOnGrid();
        appBorderPane.setCenter(sheetComponent);
    }


    public void setContentAndBindsOnGrid()
    {
        int rows = engine.getSheetStatus().getLayout().getRows();
        int columns = engine.getSheetStatus().getLayout().getColumns();
        // Dynamically populate the GridPane with TextFields
        //all of this should be in the appController ??

        cellsValue = new StringProperty[rows + 1][columns + 1];

        // Initialize each element with a SimpleStringProperty
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                cellsValue[i][j] = new SimpleStringProperty("");
            }
        }

        for (int row = 0; row <= rows; row++) {
            for (int col = 0; col <= columns; col++) {

                TextField textField = new TextField();
                textField.setEditable(false);  // Disable editing

                textField.setMaxWidth(Double.MAX_VALUE);  // Allow TextField to stretch horizontally
                textField.setMaxHeight(Double.MAX_VALUE);  // Allow TextField to stretch vertically

                // Set font size and alignment to match FXML
                textField.setFont(Font.font("System", 12));
                textField.setAlignment(javafx.geometry.Pos.CENTER);

                // Dynamically set the content of the TextField

                if (row == 0 && col > 0) {
                    cellsValue[row][col].setValue(Character.toString((char) ('A' + col - 1)));  // Column headers (A, B, C, etc.)
                } else if (col == 0 && row > 0) {
                    cellsValue[row][col].setValue(Integer.toString(row));  // Row headers (1, 2, 3, etc.)
                } else {
                    if (row != 0 || col != 0) {
                        //getting cell
                        CellGetters cell = engine.getCellStatus(row - 1, col - 1);
                        final String originalValue;
                        final String coord;
                        final String lastUpdateVersion;

                        if (cell != null) //exist
                        {
                            cellsValue[row][col].setValue(cell.getEffectiveValue().getValue().toString());
                            originalValue = cell.getOriginalValue();
                            coord = cell.getCoordinate().toString();
                            lastUpdateVersion = String.valueOf(cell.getVersion());

                        } else { //empty cell
    //                        cellsValue[row][col].setValue("");
                            originalValue = "";
                            coord = CoordinateFactory.createCoordinate(row - 1,col - 1).toString();
                            lastUpdateVersion = "";

                        }
                        //add listener to focus.
                        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                            this.focusChanged(newValue, coord, originalValue, lastUpdateVersion);
                        });
                    }
                }

                textField.textProperty().bind(cellsValue[row][col]); //cellsValue[row][col] = stringProperty.
                // Add TextField to cells

                // Add TextField to the GridPane
                sheetComponentController.gridPane.add(textField, col, row); //gridPane is public just for flow.

                // Set alignment for grid children if necessary
                GridPane.setHgrow(textField, Priority.ALWAYS);
                GridPane.setVgrow(textField, Priority.ALWAYS);
                GridPane.setHalignment(textField, HPos.CENTER);
                GridPane.setValignment(textField, VPos.CENTER);
            }
        }
    }

    public void focusChanged(boolean newValue, String coord, String originalValue, String lastUpdateVersion)
    {
        if (newValue)
        {
            //change text box cell id
            cellInFocus.setCoordinate(coord);
            //change orignal value
            cellInFocus.setOriginalValue(originalValue);
            //change version
            cellInFocus.setLastUpdateVersion(lastUpdateVersion);
        }
    }

}
