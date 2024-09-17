package commands;

import app.AppController;
import commands.operations.filter.FilterController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ranges.operations.add.AddRangeController;

import java.io.IOException;
import java.net.URL;


public class CommandsController {

    private static final String FILTER_POPUP_FXML_INCLUDE_RESOURCE = "operations/filter/filter.fxml";

    private AppController mainController;

    @FXML
    private Button buttonFilter;

    @FXML
    private Button buttonResetToDefault;

    @FXML
    private Button buttonSort;

    @FXML
    private ColorPicker colorPickerBackgroundColor;

    @FXML
    private ColorPicker colorPickerTextColor;

    @FXML
    private ComboBox<Pos> comboBoxAlignment;

    @FXML
    private Spinner<Integer> spinnerHeight;

    @FXML
    private Spinner<Integer> spinnerWidth;

    private IntegerProperty heightProperty;

    private IntegerProperty widthProperty;

    private Stage filterStage;

    public AppController getMainController() {
        return mainController;
    }

    public Button getButtonResetToDefault() {
        return buttonResetToDefault;
    }

    public ColorPicker getColorPickerBackgroundColor() {
        return colorPickerBackgroundColor;
    }

    public ColorPicker getColorPickerTextColor() {
        return colorPickerTextColor;
    }

    public ComboBox<Pos> getComboBoxAlignment() {
        return comboBoxAlignment;
    }

    public Spinner<Integer> getSpinnerHeight() {
        return spinnerHeight;
    }

    public Spinner<Integer> getSpinnerWidth() {
        return spinnerWidth;
    }

    public int getHeightProperty() {
        return heightProperty.get();
    }

    public IntegerProperty heightPropertyProperty() {
        return heightProperty;
    }

    public int getWidthProperty() {
        return widthProperty.get();
    }

    public IntegerProperty widthPropertyProperty() {
        return widthProperty;
    }

    public CommandsController() {
        heightProperty = new SimpleIntegerProperty();
        widthProperty = new SimpleIntegerProperty();
    }

    @FXML
    private void initialize() {

    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void alignmentAction(ActionEvent event) {
        int selectedIndex = comboBoxAlignment.getSelectionModel().getSelectedIndex();
        switch (selectedIndex) {
            case 0:
                mainController.alignCells(Pos.CENTER_LEFT);
                break;
            case 1:
                mainController.alignCells(Pos.CENTER);
                break;
            case 2:
                mainController.alignCells(Pos.CENTER_RIGHT);
                break;
        }
    }

    @FXML
    void backgroundColorAction(ActionEvent event) {
        mainController.changeSheetCellBackgroundColor(colorPickerBackgroundColor.getValue());
    }

    @FXML
    void filterAction(ActionEvent event) throws IOException {
        activateFilterPopup(FILTER_POPUP_FXML_INCLUDE_RESOURCE, "Filter");
    }

    @FXML
    void resetToDefaultAction(ActionEvent event) {
        mainController.resetCellsToDefault();
    }

    @FXML
    void sortAction(ActionEvent event) {

    }

    @FXML
    void textColorAction(ActionEvent event) {

    }

    public void init() {
        BooleanProperty showCommandsProperty = this.mainController.showCommandsProperty();
        buttonResetToDefault.disableProperty().bind(showCommandsProperty.not());
        buttonSort.disableProperty().bind(showCommandsProperty.not());
        buttonFilter.disableProperty().bind(showCommandsProperty.not());
        spinnerWidth.disableProperty().bind(showCommandsProperty.not());
        spinnerHeight.disableProperty().bind(showCommandsProperty.not());
        comboBoxAlignment.disableProperty().bind(showCommandsProperty.not());
        colorPickerBackgroundColor.disableProperty().bind(showCommandsProperty.not());
        colorPickerTextColor.disableProperty().bind(showCommandsProperty.not());

        // Set the alignment options in the combo box and initiate it to Left as default.
        ObservableList<Pos> columnAlignmentOptions = FXCollections.observableArrayList(Pos.CENTER_LEFT, Pos.CENTER, Pos.CENTER_RIGHT);
        comboBoxAlignment.setItems(columnAlignmentOptions);
        comboBoxAlignment.getSelectionModel().selectFirst();

        // column width picker
        SpinnerValueFactory<Integer> widthValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1);
        spinnerWidth.setValueFactory(widthValueFactory);

        spinnerWidth
                .valueProperty()
                .addListener((observable, oldValue, newValue) -> mainController.changeSheetColumnWidth(newValue));


        // row height picker
        SpinnerValueFactory<Integer> heightValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1);
        spinnerHeight.setValueFactory(heightValueFactory);

        spinnerHeight
                .valueProperty()
                .addListener((observable, oldValue, newValue) -> mainController.changeSheetRowHeight(newValue));

        colorPickerBackgroundColor
                .valueProperty()
                .addListener((observable, oldValue, newValue) -> mainController.changeSheetCellBackgroundColor(newValue));

        colorPickerTextColor
                .valueProperty()
                .addListener((observableValue, oldValue, newValue) -> mainController.changeSheetTextColor(newValue));

//        // set initial values
//        Platform.runLater(() -> {
//            comboBoxAlignment.getSelectionModel().selectFirst();
//
//            spinnerWidth.getValueFactory().setValue(100);
//
//            spinnerHeight.getValueFactory().setValue(40);
//        });
    }

    public void changeColumnWidth(int prefWidth) {
        spinnerWidth.getValueFactory().setValue(prefWidth);
    }

    public void changeRowHeight(int prefHeight) {
        spinnerHeight.getValueFactory().setValue(prefHeight);
    }

    public void changeColumnAlignment(Pos alignment) {
        comboBoxAlignment.getSelectionModel().select(alignment);
    }

    public void changeCellBackgroundColor(Color color) {
        colorPickerBackgroundColor.setValue(color);
    }

    public void changeCellTextColor(Color color) {
        colorPickerTextColor.setValue(color);
    }

    private void activateFilterPopup(String resource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(resource);
        fxmlLoader.setLocation(url);
        Parent popupRoot = fxmlLoader.load(url.openStream());

        FilterController filterController = fxmlLoader.getController();

        filterController.setMainController(this);

        this.filterStage = new Stage();
        filterStage.initModality(Modality.APPLICATION_MODAL);
        filterStage.setTitle(title);

        Scene popupScene = new Scene(popupRoot, 700, 100);
        filterStage.setResizable(false);
        filterStage.setScene(popupScene);

        filterStage.showAndWait();
    }
}
