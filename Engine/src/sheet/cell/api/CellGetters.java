package sheet.cell.api;

import expression.api.Data;
import sheet.coordinate.api.Coordinate;

import java.util.Set;

public interface CellGetters {
    Coordinate GetCoordinate();
    int GetVersion();
    String GetOriginalValue();
    Data GetEffectiveValue();
    Set<Cell> GetInfluenceFrom();
    Set<Cell> GetInfluenceOn();
}
