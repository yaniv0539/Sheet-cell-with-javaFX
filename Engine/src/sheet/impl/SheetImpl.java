package sheet.impl;

import sheet.api.Sheet;
import sheet.cell.api.Cell;
import sheet.cell.impl.CellImpl;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SheetImpl implements Sheet {

    int version;
    private final Map<Coordinate, Cell> activeCells;

    public SheetImpl(int numberOfRows, int numberOfColumns) {
        version = 0;
        activeCells = new HashMap<>();
    }

    @Override
    public Cell getCell(int row, int column) {
        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
    }

    @Override
    public void setCell(int row, int column, String originalValue) {
        Optional
                .ofNullable(getCell(row, column))
                .ifPresentOrElse(cell -> cell.setOriginalValue(originalValue),
                        () -> activeCells.put(CoordinateFactory.createCoordinate(row, column),
                                new CellImpl(CoordinateFactory.createCoordinate(row, column),
                                        this.version,
                                        originalValue)));
    }
}
