package sheet.api;

import sheet.coordinate.api.Coordinate;

import java.util.Map;

public interface SheetSetters {
    void setCell(Coordinate coordinate, String value);
    void setCells(Map<Coordinate, String> originalValues);
    void setVersion(int version);
}
