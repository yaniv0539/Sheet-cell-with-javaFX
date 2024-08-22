package sheet.cell.api;

import expression.api.Data;
import sheet.coordinate.api.Coordinate;
import java.util.Set;

public interface Cell {

    Coordinate getCoordinate();
    int getVersion();
    String getOriginalValue();
    Data getEffectiveValue();
    Set<Cell> getInfluenceFrom();
    Set<Cell> getInfluenceOn();
    void computeEffectiveValue();
    void setOriginalValue(String originalValue);
    void setInfluenceOn(Set<Cell> influenceOn);
    void setInfluenceFrom(Set<Cell> influenceFrom);
    void addInfluenceOn(Cell influenceOn);
    void addInfluenceFrom(Cell influenceFrom);

}