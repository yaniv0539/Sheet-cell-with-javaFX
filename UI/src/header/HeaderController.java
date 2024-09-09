package header;

import app.AppController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sheet.SheetController;

public class HeaderController {

        @FXML
        public Button buttonUpdateCell;

        @FXML
        public Button buttonUploadXmlFile;

        @FXML
        public Label labelCellId;

        @FXML
        public Label labelOriginalValue;

        @FXML
        public Label labelVersionSelector;

        @FXML
        public Label lableFileName;

        @FXML
        public TextField textFieldCellId;

        @FXML
        public TextField textFieldFileName;

        @FXML
        public TextField textFieldOrignalValue;

        @FXML
        public TextField textFieldVersionSelector;

        private AppController mainController;

        public void setMainController(AppController mainController) {
                this.mainController = mainController;
        }

        //TEST
        public void bindCellIdTextField(StringProperty strProp) {
                textFieldCellId.textProperty().bind(strProp);
        }

        //test nor real implementation
        @FXML
        void buttonUpdateCellAction(ActionEvent event) {
                //TEST
                //mainController.focusChanged(new SimpleBooleanProperty(),true,true);
        }

        @FXML
        public  void buttonUploadXmlFileAction(ActionEvent event) {
            mainController.uploadXml();
        }

        @FXML
        void textFieldFileNameAction(ActionEvent event) {

        }

        @FXML
        void textFieldOrignalValueAction(ActionEvent event) {

        }

        @FXML
        void textFieldVersionSelectorAction(ActionEvent event) {

        }

    }
