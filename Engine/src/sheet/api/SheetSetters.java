package sheet.api;

import sheet.coordinate.api.Coordinate;
import sheet.range.api.Range;
import sheet.range.api.RangeGetters;
import sheet.range.boundaries.api.Boundaries;
import sheet.range.boundaries.api.BoundariesGetters;

import java.util.Map;

public interface SheetSetters {
    void setCell(Coordinate coordinate, String value);
    void setCells(Map<Coordinate, String> originalValues);
    void setVersion(int version);
    void addRange(String name, Boundaries boundaries);
    void deleteRange(RangeGetters range);
}
