package sheet.api;

import expression.api.Data;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.api.CoordinateGetters;
import sheet.layout.api.Layout;

import java.util.Map;

public interface SheetGetters {
    String GetName();
    Layout GetLayout();
    int GetVersion();
    Cell GetCell(Coordinate coordinate);
    Map<CoordinateGetters, CellGetters> GetActiveCells();
    Data GetCellData(String cellId);
}
