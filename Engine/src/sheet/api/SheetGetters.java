package sheet.api;

import expression.api.Data;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.api.CoordinateGetters;
import sheet.layout.api.Layout;

import java.util.Map;

public interface SheetGetters {
    String getName();
    Layout getLayout();
    int getVersion();
    Cell getCell(Coordinate coordinate);
    Map<CoordinateGetters, CellGetters> getActiveCells();
    Data getCellData(String cellId);
}
