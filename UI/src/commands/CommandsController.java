package commands;

import app.AppController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;


public class CommandsController {

    private static final String SET_COLUMN_SIZE_FXML_INCLUDE_RESOURCE = "visual/column/size/columnSize.fxml";

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
    private ComboBox<String> comboBoxAlignment;

    @FXML
    private Spinner<Integer> spinnerHeight;

    @FXML
    private Spinner<Integer> spinnerWidth;

    private IntegerProperty heightProperty;

    private IntegerProperty widthProperty;

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

    public ComboBox<String> getComboBoxAlignment() {
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

    public void commandsInitializeBinding() {

        // Set the alignment options in the combo box and initiate it to Left as default.
        ObservableList<String> columnAlignmentOptions = FXCollections.observableArrayList("Left", "Center", "Right");
        comboBoxAlignment.setItems(columnAlignmentOptions);
        comboBoxAlignment.getSelectionModel().selectFirst();

        // column width picker
        spinnerWidth
                .valueProperty()
                .addListener((observable, oldValue, newValue) -> mainController.changeColumnWidth(newValue));

        SpinnerValueFactory<Integer> widthValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 200, 100, 1);
        spinnerWidth.setValueFactory(widthValueFactory);

        // row height picker
        spinnerHeight
                .valueProperty()
                .addListener((observable, oldValue, newValue) -> mainController.changeRowHeight(newValue));

        SpinnerValueFactory<Integer> heightValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 100, 40, 1);
        spinnerHeight.setValueFactory(heightValueFactory);

        // set initial values
        Platform.runLater(() -> {
            comboBoxAlignment.getSelectionModel().selectFirst();

            spinnerWidth.getValueFactory().setValue(100);

            spinnerHeight.getValueFactory().setValue(40);
        });
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
    void filterAction(ActionEvent event) {

    }

    @FXML
    void resetToDefaultAction(ActionEvent event) {

    }

    @FXML
    void sortAction(ActionEvent event) {

    }

    public void init() {
        BooleanProperty isSelectedProperty = this.mainController.isFileSelectedProperty();
        buttonResetToDefault.disableProperty().bind(isSelectedProperty.not());
        buttonSort.disableProperty().bind(isSelectedProperty.not());
        buttonFilter.disableProperty().bind(isSelectedProperty.not());
        spinnerWidth.disableProperty().bind(isSelectedProperty.not());
        spinnerHeight.disableProperty().bind(isSelectedProperty.not());
        comboBoxAlignment.disableProperty().bind(isSelectedProperty.not());
        colorPickerBackgroundColor.disableProperty().bind(isSelectedProperty.not());
        colorPickerTextColor.disableProperty().bind(isSelectedProperty.not());
    }
}
