package ranges;

import app.AppController;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ranges.operations.add.AddRangeController;
import sheet.range.api.RangeGetters;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class RangesController {

    private static final String ADD_RANGE_POPUP_FXML_INCLUDE_RESOURCE = "operations/add/addRange.fxml";
    private static final String DELETE_RANGE_POPUP_FXML_INCLUDE_RESOURCE = "operations/delete/deleteRange.fxml";

    @FXML
    private Button buttonAddRange;

    @FXML
    private Button buttonDeleteRange;

    @FXML
    private TableColumn<RangeGetters, String> tableActiveRanges;

    @FXML
    private TableView<RangeGetters> tableViewActiveRanges;

    private AppController mainController;

    private ObservableList<RangeGetters> ranges;

    public RangesController() {
        ranges = FXCollections.observableArrayList();
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void addRangeAction(ActionEvent event) throws IOException {
        activateRangeAction(ADD_RANGE_POPUP_FXML_INCLUDE_RESOURCE, "Add Range");
    }

    @FXML
    void deleteRangeAction(ActionEvent event) throws IOException {
        activateRangeAction(DELETE_RANGE_POPUP_FXML_INCLUDE_RESOURCE, "Delete Range");
    }

    @FXML
    void showRangeAction(ActionEvent event) throws IOException {
        // TODO: check if a range had been selected, if not throw exception. Else, color the selected range on board.
    }

    void activateRangeAction(String resource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(resource);
        fxmlLoader.setLocation(url);
        Parent popupRoot = fxmlLoader.load(url.openStream());

        AddRangeController addRangeController = fxmlLoader.getController();

        addRangeController.setMainController(this);

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Scene popupScene = new Scene(popupRoot, 415, 100);
        popupStage.setResizable(false);
        popupStage.setScene(popupScene);

        popupStage.showAndWait();
    }

    public void init() {
        BooleanProperty showRangesProperty = this.mainController.showRangesProperty();
        buttonAddRange.disableProperty().bind(showRangesProperty.not());
        buttonDeleteRange.disableProperty().bind(showRangesProperty.not());
        tableViewActiveRanges.disableProperty().bind(showRangesProperty.not());
        tableActiveRanges.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewActiveRanges.setItems(ranges);
    }

    public void addRange(String name, String boundaries) {
        this.mainController.addRange(name, boundaries);
        ranges.add(this.mainController.getRange(name));
        tableViewActiveRanges.refresh();
    }

    public void uploadRanges(Set<RangeGetters> ranges) {
        this.ranges.addAll(ranges);
        tableViewActiveRanges.refresh();
    }
}
