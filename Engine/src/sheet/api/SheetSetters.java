package sheet.api;

import sheet.coordinate.api.Coordinate;
import sheet.range.api.Range;

import java.util.Map;

public interface SheetSetters {
    void setCell(Coordinate coordinate, String value);
    void setCells(Map<Coordinate, String> originalValues);
    void setVersion(int version);
    void addRange(Range range);
    void removeRange(Range range);
}
