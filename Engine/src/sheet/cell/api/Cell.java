package sheet.cell.api;

import sheet.coordinate.api.Coordinate;
import java.util.Set;

public interface Cell {

    Coordinate getCoordinate();
    int getVersion();
    String getOriginalValue();
    void setOriginalValue(String originalValue);
    EffectiveValue getEffectiveValue();
    Set<Cell> getDependentCells();
    Set<Cell> getInfluencedCells();
}

// UI:
// e.setCell("A4", "{MINUS,A3,A4}");
//
// Engine:
//
// sheet.setCell(row, col, Expression data);
// Sheet:
// cell.set(data);
//cell
// "4" System.int("4")

// UI:
// e.updateCell("A4", "Itay")
// Engine
// sheet.updateCell(row, col, Expresion data)
// cell.set(data)
// Cell:
// A4: {MINUS,A5,A6}
// Listeners: {B5, B7}
// {MINUS,{PLUS,3,4},{POW,2,2}}
//