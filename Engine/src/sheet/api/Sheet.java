package sheet.api;

import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;
import sheet.layout.api.Layout;

import java.util.Map;

public interface Sheet {
    String getName();
    Layout getLayout();
    int getVersion();
    Cell getCell(Coordinate coordinate);
    Map<Coordinate, Cell> getActiveCells();
    void setCell(Coordinate coordinate, String value);
    void setCells(Map<Coordinate, String> originalValues);
    void setVersion(int version);
}
