package app;

import commands.CommandsController;
import engine.api.Engine;
import engine.impl.EngineImpl;
import header.HeaderController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelUI.api.EffectiveValuesPoolProperty;
import modelUI.api.EffectiveValuesPoolPropertyReadOnly;
import modelUI.api.FocusCellProperty;
import modelUI.impl.EffectiveValuesPoolPropertyImpl;
import modelUI.impl.FocusCellPropertyImpl;
import modelUI.impl.VersionDesignManager;
import progress.ProgressController;
import ranges.RangesController;
import sheet.SheetController;
import sheet.api.SheetGetters;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.range.api.RangeGetters;
import sheet.range.boundaries.api.Boundaries;

import java.util.HashSet;
import java.util.List;
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
    private ProgressController progressComponentController;
    private Stage loadingStage;
    private Stage primaryStage;

    private VersionDesignManager versionDesignManager;
    private FocusCellProperty cellInFocus;
    private SheetGetters currentSheet;
    private EffectiveValuesPoolProperty effectiveValuesPool;
    private Engine engine;
    private boolean OperationView;


    public AppController() {
        this.engine = EngineImpl.create();
        this.showHeaders = new SimpleBooleanProperty(false);
        this.showRanges = new SimpleBooleanProperty(false);
        this.showCommands = new SimpleBooleanProperty(false);
        this.cellInFocus = new FocusCellPropertyImpl();
        this.effectiveValuesPool = new EffectiveValuesPoolPropertyImpl();
        this.progressComponentController = new ProgressController();
        this.loadingStage = new Stage();
        this.versionDesignManager = new VersionDesignManager();
        OperationView = false;
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && commandsComponentController != null && rangesComponentController != null) {
            headerComponentController.setMainController(this);
            commandsComponentController.setMainController(this);
            rangesComponentController.setMainController(this);
            versionDesignManager.setMainController(this);

            headerComponentController.init();
            commandsComponentController.init();
            rangesComponentController.init();
            initLoadingStage();

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

    private void initLoadingStage() {

        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.setScene(new Scene(progressComponentController.getProgressVbox()));
    }

    public void uploadXml(String path)
    {
        Task<Boolean> FileTask = engine.loadFileTask(path);

        progressComponentController.init(FileTask);

        FileTask.setOnSucceeded(workerStateEvent -> {
            loadingStage.close();
            onFinishLoadingFile();
        });

        FileTask.setOnFailed(workerStateEvent -> {
            loadingStage.close();
            //do something
        });

        loadingStage.show();

        new Thread(FileTask).start();
    }

    private void onFinishLoadingFile() {
        showHeaders.set(true);
        showRanges.set(true);
        headerComponentController.getSplitMenuButtonSelectVersion().setDisable(false);
        commandsComponentController.getButtonFilter().setDisable(false);
        commandsComponentController.resetButtonFilter();
        setEffectiveValuesPoolProperty(engine.getSheetStatus(), this.effectiveValuesPool);
        setSheet();
        this.currentSheet = engine.getSheetStatus();
        headerComponentController.clearVersionButton();
        headerComponentController.addMenuOptionToVersionSelection(String.valueOf(engine.getVersionsManagerStatus().getVersions().size()));
        rangesComponentController.uploadRanges(engine.getRanges());
        saveDesignVersion(sheetComponentController.getGridPane());
    }

    private void setSheet() {
        sheetComponentController = new SheetController();
        sheetComponentController.setMainController(this);
        sheetComponent = sheetComponentController.getInitializedSheet(engine.getSheetStatus().getLayout(), effectiveValuesPool);
        appBorderPane.setCenter(sheetComponent);
    }


    private void setEffectiveValuesPoolProperty(SheetGetters sheetToView, EffectiveValuesPoolProperty effectiveValuesPool) {

        Map<Coordinate,CellGetters> map = sheetToView.getActiveCells();

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

        if (newValue && !OperationView )
        {
            showCommands.set(currentSheet.getVersion() == engine.getVersionsManagerStatus().getVersions().size());
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
        this.currentSheet = engine.getSheetStatus();
        setEffectiveValuesPoolProperty(engine.getSheetStatus(), this.effectiveValuesPool);
        saveDesignVersion(sheetComponentController.getGridPane());
        //need to make in engine version manager, current version number.
        headerComponentController.addMenuOptionToVersionSelection(String.valueOf(engine.getVersionsManagerStatus().getVersions().size()));

    }

    private void saveDesignVersion(GridPane gridPane) {
        versionDesignManager.addVersion(gridPane);
    }

    public void viewSheetVersion(String numberOfVersion) {
        //TODO:need to change it to some toggle on/off for disable enable
        //TODO: need to put a current version showing, and if we pick the newest version the button would not be disable.
        //TODO: the disable make exeption.
        currentSheet = engine.getVersionsManagerStatus().getVersion(Integer.parseInt(numberOfVersion));
        showCommands.set(Integer.parseInt(numberOfVersion) == engine.getVersionsManagerStatus().getVersions().size());
        showRanges.set(Integer.parseInt(numberOfVersion) == engine.getVersionsManagerStatus().getVersions().size());
        showHeaders.set(Integer.parseInt(numberOfVersion) == engine.getVersionsManagerStatus().getVersions().size());
        setEffectiveValuesPoolProperty(currentSheet, this.effectiveValuesPool);
        resetSheetToVersionDesign(Integer.parseInt(numberOfVersion));
    }

    private void resetSheetToVersionDesign(int numberOfVersion) {

        sheetComponentController.setGridPaneDesign(versionDesignManager.getVersionDesign(numberOfVersion));
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

    public void changeCommandsCellBackgroundColor(Color color) {
        commandsComponentController.changeCellBackgroundColor(color);
    }

    public void changeCommandsCellTextColor(Color color) {
        commandsComponentController.changeCellTextColor(color);
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

    public void changeSheetTextColor(Color color) {
        sheetComponentController.changeCellTextColor(color);
    }

    public void resetCellsToDefault() {
        sheetComponentController.resetCellsToDefault(CoordinateFactory.toCoordinate(cellInFocus.getCoordinate().get()));
    }

    public void addRange(String name, String boundaries) {
        engine.addRange(name, boundaries);
    }

    public RangeGetters getRange(String name) {
        return engine.getRange(name);
    }

    public boolean deleteRange(RangeGetters range) {
        if (!isRangeUsed(range)) {
            engine.deleteRange(range.getName());
            return true;
        } else {
            return false;
        }
    }

    private boolean isRangeUsed(RangeGetters range) {
        for (CellGetters cell : this.currentSheet.getActiveCells().values()) {
            if (cell.getOriginalValue().toUpperCase().contains(range.getName())) {
                return true;
            }
        }
        return false;
    }

    public void paintRangeOnSheet(RangeGetters range, Color color) {
        this.sheetComponentController.paintRangeOnSheet(range, color);
    }

    public void getFilteredSheet(Boundaries boundariesToFilter, String filteringByColumn, List<String> filteringByValues) {

        OperationView = true;
        SheetGetters filteredSheet = engine.filter(boundariesToFilter, filteringByColumn, filteringByValues, currentSheet.getVersion());

        EffectiveValuesPoolProperty effectiveValuesPoolProperty = new EffectiveValuesPoolPropertyImpl();
        setEffectiveValuesPoolProperty(filteredSheet, effectiveValuesPoolProperty);

        SheetController sheetComponentController = new SheetController();
        sheetComponentController.setMainController(this);
        ScrollPane sheetComponent = sheetComponentController.getInitializedSheet(filteredSheet.getLayout(), effectiveValuesPoolProperty);
        appBorderPane.setCenter(sheetComponent);

        showHeaders.set(false);
        showRanges.set(false);
        showCommands.set(false);
        headerComponentController.getSplitMenuButtonSelectVersion().setDisable(true);
        commandsComponentController.getButtonFilter().setDisable(false);
    }

    public void resetFilter() {

        OperationView = false;
        viewSheetVersion(String.valueOf(currentSheet.getVersion()));
        headerComponentController.getSplitMenuButtonSelectVersion().setDisable(false);
//        showHeaders.set(true);
//        showRanges.set(true);
//        headerComponentController.getSplitMenuButtonSelectVersion().setDisable(false);
//        commandsComponentController.getButtonFilter().setDisable(false);

        appBorderPane.setCenter(sheetComponent);
    }

    public boolean isBoundariesValidForCurrentSheet(Boundaries boundaries) {
        return currentSheet.isRangeInBoundaries(boundaries);
    }

    public Color getBackground(TextField tf) {
        return sheetComponentController.getTextFieldBackgroundColor(tf.getBackground());
    }

    public void resetRangeOnSheet(RangeGetters selectedItem) {
        sheetComponentController.resetRangeOnSheet(selectedItem);
    }
}
