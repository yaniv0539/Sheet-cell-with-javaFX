package app;

import commands.CommandsController;
import engine.api.Engine;
import engine.impl.EngineImpl;
import header.HeaderController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelUI.api.EffectiveValuesPoolProperty;
import modelUI.api.EffectiveValuesPoolPropertyReadOnly;
import modelUI.api.FocusCellProperty;
import modelUI.impl.EffectiveValuesPoolPropertyImpl;
import modelUI.impl.FocusCellPropertyImpl;
import ranges.RangesController;
import sheet.SheetController;
import sheet.api.SheetGetters;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.api.CoordinateGetters;
import sheet.coordinate.impl.CoordinateFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class AppController {

    @FXML private BorderPane appBorderPane;
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane commandsComponent;
    @FXML private CommandsController commandsComponentController;
    @FXML private ScrollPane rangesComponent;
    @FXML private RangesController rangesComponentController;

    private SimpleBooleanProperty showCommands;
    private SimpleBooleanProperty showRanges;
    private SimpleBooleanProperty showHeaders;
    private ScrollPane sheetComponent;
    private SheetController sheetComponentController;
    private Stage primaryStage;


    private FocusCellProperty cellInFocus;
    private SheetGetters currentSheet;
    private EffectiveValuesPoolProperty effectiveValuesPool;
    private Engine engine;

    public AppController() {
        this.engine = EngineImpl.create();
        this.showHeaders = new SimpleBooleanProperty(false);
        this.showRanges = new SimpleBooleanProperty(false);
        this.showCommands = new SimpleBooleanProperty(false);
        this.cellInFocus = new FocusCellPropertyImpl();
        this.effectiveValuesPool = new EffectiveValuesPoolPropertyImpl();
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && commandsComponentController != null && rangesComponentController != null) {
            headerComponentController.setMainController(this);
            commandsComponentController.setMainController(this);
            rangesComponentController.setMainController(this);

            headerComponentController.init();
            commandsComponentController.init();
            rangesComponentController.init();

            //cell in focus init
            cellInFocus.getDependOn().addListener((ListChangeListener<Coordinate>) change -> sheetComponentController.changeColorDependedCoordinate(change));
            cellInFocus.getInfluenceOn().addListener((ListChangeListener<Coordinate>) change -> sheetComponentController.changeColorInfluenceCoordinate(change));
        }
    }

    public SimpleBooleanProperty showCommandsProperty() {
        return showCommands;
    }

    public SimpleBooleanProperty showRangesProperty() {
        return showRanges;
    }

    public SimpleBooleanProperty showHeadersProperty() {
        return showHeaders;
    }

    public FocusCellProperty getCellInFocus() {
        return cellInFocus;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void uploadXml(String path)
    {
        engine.readXMLInitFile(path);
        showHeaders.set(true);
        showRanges.set(true);
        headerComponentController.getSplitMenuButtonSelectVersion().setDisable(false);
        setEffectiveValuesPoolProperty(engine.getSheetStatus());
        setSheet();
        this.currentSheet = engine.getSheetStatus();
        headerComponentController.clearVersionButton();
        headerComponentController.addMenuOptionToVersionSelection(String.valueOf(engine.getVersionsManagerStatus().getVersions().size()));
    }

    private void setSheet() {
        sheetComponentController = new SheetController();
        sheetComponentController.setMainController(this);
        sheetComponent = sheetComponentController.getInitializedSheet(engine.getSheetStatus().getLayout());
        appBorderPane.setCenter(sheetComponent);
    }


    private void  setEffectiveValuesPoolProperty(SheetGetters sheetToView) {

        Map<CoordinateGetters,CellGetters> map = sheetToView.getActiveCells();

        for(int row = 0; row < sheetToView.getLayout().getRows(); row++) {
            for(int column = 0; column < sheetToView.getLayout().getColumns(); column++) {
              Coordinate coordinate = CoordinateFactory.createCoordinate(row,column);
              CellGetters cell = map.get(coordinate);
                if(cell != null){
                    effectiveValuesPool.addEffectiveValuePropertyAt(coordinate, cell.getEffectiveValue().toString());
                }
                else {
                    effectiveValuesPool.addEffectiveValuePropertyAt(coordinate, "");
                }
            }
        }
    }

    public void focusChanged(boolean newValue, Coordinate coordinate) {

        showCommands.set(currentSheet.getVersion() == engine.getVersionsManagerStatus().getVersions().size());

        if (newValue)
        {
            Cell cell = currentSheet.getCell(coordinate);
            cellInFocus.setCoordinate(coordinate.toString());

            if (cell != null) {
                cellInFocus.setOriginalValue(cell.getOriginalValue());
                cellInFocus.setLastUpdateVersion(String.valueOf(cell.getVersion()));
                cellInFocus.setDependOn(cell.getInfluenceFrom().stream()
                        .map(CellGetters::getCoordinate)
                        .collect(Collectors.toSet()));
                cellInFocus.setInfluenceOn(cell.getInfluenceOn().stream()
                        .map(CellGetters::getCoordinate)
                        .collect(Collectors.toSet()));
            } else {
                cellInFocus.setOriginalValue("");
                cellInFocus.setLastUpdateVersion("");
                cellInFocus.setDependOn(new HashSet<>());
                cellInFocus.setInfluenceOn(new HashSet<>());
            }
        }
        else{
            cellInFocus.setDependOn(new HashSet<>());
            cellInFocus.setInfluenceOn(new HashSet<>());
        }
    }

    public EffectiveValuesPoolPropertyReadOnly getEffectiveValuesPool() {
        return effectiveValuesPool;
    }

    public void updateCell() {
        engine.updateCellStatus(cellInFocus.getCoordinate().get(), cellInFocus.getOriginalValue().get());
        setEffectiveValuesPoolProperty(engine.getSheetStatus());
        //need to make in engine version manager, current version number.
        headerComponentController.addMenuOptionToVersionSelection(String.valueOf(engine.getVersionsManagerStatus().getVersions().size()));

    }

    public void viewSheetVersion(String numberOfVersion) {
        //TODO:need to change it to some toggle on/off for disable enable
        //TODO: need to put a current version showing, and if we pick the newest version the button would not be disable.
        //TODO: the disable make exeption.
        currentSheet = engine.getVersionsManagerStatus().getVersion(Integer.parseInt(numberOfVersion));
        showCommands.set(Integer.parseInt(numberOfVersion) == engine.getVersionsManagerStatus().getVersions().size());
        showRanges.set(Integer.parseInt(numberOfVersion) == engine.getVersionsManagerStatus().getVersions().size());
        showHeaders.set(Integer.parseInt(numberOfVersion) == engine.getVersionsManagerStatus().getVersions().size());
        setEffectiveValuesPoolProperty(currentSheet);
    }

    public void changeCommandsColumnWidth(double prefWidth) {
        commandsComponentController.changeColumnWidth((int) prefWidth);
    }

    public void changeCommandsRowHeight(double prefHeight) {
        commandsComponentController.changeRowHeight((int) prefHeight);
    }

    public void changeCommandsColumnAlignment(Pos alignment) {
        commandsComponentController.changeColumnAlignment(alignment);
    }

    public void changeCommandsCellBackgroundColor(Background background) {
//        commandsComponentController.changeCellBackgroundColor()
    }

    public void changeSheetColumnWidth(int prefWidth) {
        int column =
                CoordinateFactory.parseColumnToInt(
                        CoordinateFactory.extractColumn(
                                cellInFocus
                                        .getCoordinate()
                                        .get()));
        sheetComponentController.changeColumnWidth(column, prefWidth);
    }

    public void changeSheetRowHeight(int prefHeight) {
        int row =
                CoordinateFactory.extractRow(
                        cellInFocus
                                .getCoordinate()
                                .get());
        sheetComponentController.changeRowHeight(row, prefHeight);
    }

    public void alignCells(Pos pos) {
        int column =
                CoordinateFactory.parseColumnToInt(
                        CoordinateFactory.extractColumn(
                                cellInFocus
                                        .getCoordinate()
                                        .get()));
        sheetComponentController.changeColumnAlignment(column, pos);
    }

    public void changeSheetCellBackgroundColor(Color color) {
        sheetComponentController.changeCellBackgroundColor(color);
    }


}
