package ranges.operations.add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddRangeController {

    private static final String CELL_RANGE_REGEX = "^[A-Z]+\\d+\\.\\.[A-Z]+\\d+$";

    @FXML
    private Button buttonSubmit;

    @FXML
    private TextField textFieldRangeBoundaries;

    @FXML
    private TextField textFieldRangeName;

    @FXML
    void submitAction(ActionEvent event) {
        if (textFieldRangeName.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Range name cannot be empty");
        }

        if (!textFieldRangeBoundaries.getText().matches(CELL_RANGE_REGEX)) {
            throw new NumberFormatException();
        }

        String[] cells = textFieldRangeBoundaries.getText().split("\\.\\.");

        
    }

}
