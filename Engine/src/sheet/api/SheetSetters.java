package sheet.api;

import sheet.coordinate.api.Coordinate;

import java.util.Map;

public interface SheetSetters {
    void SetCell(Coordinate coordinate, String value);
    void SetCells(Map<Coordinate, String> originalValues);
    void SetVersion(int version);
}
