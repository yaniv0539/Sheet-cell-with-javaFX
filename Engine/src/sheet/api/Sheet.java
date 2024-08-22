package sheet.api;

import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;
import sheet.layout.api.Layout;

import java.util.Map;

public interface Sheet {
    String getName();
    Layout getLayout();
    int getVersion();
    Cell getCell(int row, int column);
    Map<Coordinate, Cell> getActiveCells();
    void setCell(String cellName, String value);
    void setVersion(int version);
}
