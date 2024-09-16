package sheet.api;

import expression.api.Data;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.api.CoordinateGetters;
import sheet.layout.api.LayoutGetters;
import sheet.range.api.Range;
import sheet.range.api.RangeGetters;

import java.util.Map;
import java.util.Set;

public interface SheetGetters {
    String getName();
    LayoutGetters getLayout();
    int getVersion();
    Cell getCell(Coordinate coordinate);
    int getNumberOfCellsThatChanged();
    Map<CoordinateGetters, CellGetters> getActiveCells();
    Data getCellData(String cellId);
    Range getRange(String rangeName);
    Set<? extends RangeGetters> getRanges();
}
