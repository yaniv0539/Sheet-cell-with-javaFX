package header;

import app.AppController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import sheet.SheetController;

import java.io.File;

public class HeaderController {

        @FXML
        private Button buttonUpdateCell;

        @FXML
        private Button buttonUploadXmlFile;

        @FXML
        private Label labelCellId;

        @FXML
        private Label labelOriginalValue;

        @FXML
        private Label labelVersionSelector;

        @FXML
        private SplitMenuButton splitMenuButtonSelectVersion;

        @FXML
        private Label lableFileName;

        @FXML
        private TextField textFieldCellId;

        @FXML
        private TextField textFieldFileName;

        @FXML
        private TextField textFieldOrignalValue;

        @FXML
        private TextField textFieldLastUpdateInVersion;

        @FXML
        private TextField textFieldVersionSelector;

        private SimpleStringProperty selectedFileProperty;

        private AppController mainController;

        public Button getButtonUpdateCell() {
                return buttonUpdateCell;
        }

        public Button getButtonUploadXmlFile() {
                return buttonUploadXmlFile;
        }

        public Label getLabelCellId() {
                return labelCellId;
        }

        public Label getLabelOriginalValue() {
                return labelOriginalValue;
        }

        public Label getLabelVersionSelector() {
                return labelVersionSelector;
        }

        public SplitMenuButton getSplitMenuButtonSelectVersion() {
                return splitMenuButtonSelectVersion;
        }

        public Label getLableFileName() {
                return lableFileName;
        }

        public TextField getTextFieldCellId() {
                return textFieldCellId;
        }

        public TextField getTextFieldFileName() {
                return textFieldFileName;
        }

        public TextField getTextFieldOrignalValue() {
                return textFieldOrignalValue;
        }

        public TextField getTextFieldLastUpdateInVersion() {
                return textFieldLastUpdateInVersion;
        }

        public TextField getTextFieldVersionSelector() {
                return textFieldVersionSelector;
        }

        public AppController getMainController() {
                return mainController;
        }

        public void setMainController(AppController mainController) {
                this.mainController = mainController;
        }

        public String getSelectedFileProperty() {
                return selectedFileProperty.get();
        }

        public SimpleStringProperty selectedFilePropertyProperty() {
                return selectedFileProperty;
        }

        //TEST
        public void bindCellIdTextField(StringProperty strProp) {
                textFieldCellId.textProperty().bind(strProp);
        }

        //test nor real implementation
        @FXML
        void buttonUpdateCellAction(ActionEvent event) {
                mainController.updateCell();
        }

        @FXML
        public void buttonUploadXmlFileAction(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select xml file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
                File selectedFile = fileChooser.showOpenDialog(mainController.getPrimaryStage());
                if (selectedFile == null) {
                        //popup error
                        return;
                }
                selectedFileProperty = new SimpleStringProperty(selectedFile.getAbsolutePath());
                textFieldFileName.textProperty().bind(selectedFileProperty);
                mainController.uploadXml(selectedFileProperty.get());
        }

        @FXML //no need
        void textFieldFileNameAction(ActionEvent event) {

        }

        @FXML //maybe
        void textFieldOrignalValueAction(ActionEvent event) {
                mainController.updateCell();
        }

        @FXML
        void textFieldVersionSelectorAction(ActionEvent event) {

        }

        public void init() {
                SimpleBooleanProperty fileSelectedProperty = this.mainController.isFileSelectedProperty();
                buttonUpdateCell.disableProperty().bind(fileSelectedProperty.not());
                splitMenuButtonSelectVersion.disableProperty().bind(fileSelectedProperty.not());
                textFieldOrignalValue.disableProperty().bind(fileSelectedProperty.not());
                textFieldCellId.disableProperty().bind(fileSelectedProperty.not());
                textFieldLastUpdateInVersion.disableProperty().bind(fileSelectedProperty.not());
                textFieldCellId.textProperty().bind(this.mainController.getCellInFocus().getCoordinate());
                textFieldOrignalValue.textProperty().bindBidirectional(this.mainController.getCellInFocus().getOriginalValue());
                textFieldLastUpdateInVersion.textProperty().bind(this.mainController.getCellInFocus().getLastUpdateVersion());
        }

        public void addMenuOptionToVersionSelction(String numberOfVersion) {

                MenuItem menuItem = new MenuItem(numberOfVersion);

                // Add an action listener to the MenuItem
                menuItem.setOnAction(event -> {
                        // Update the SplitMenuButton's text to show the selected option
                        mainController.viewSheetVersion(numberOfVersion);
                });

                // Add the MenuItem to the SplitButton
                splitMenuButtonSelectVersion.getItems().add(menuItem);
        }

        public void clearVersionButton() {
                splitMenuButtonSelectVersion.getItems().clear();
        }
}
