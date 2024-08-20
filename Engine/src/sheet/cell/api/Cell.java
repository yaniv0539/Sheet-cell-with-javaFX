package sheet.cell.api;

import expression.api.Data;
import sheet.coordinate.api.Coordinate;
import java.util.Set;

public interface Cell {

    Coordinate getCoordinate();
    int getVersion();
    String getOriginalValue();
    void setOriginalValue(String originalValue);
    Data getEffectiveValue();
    Set<Cell> getDependentCells();
    Set<Cell> getInfluencedCells();
}