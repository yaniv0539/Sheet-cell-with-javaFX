package sheet.impl;

import sheet.api.Sheet;
import sheet.cell.api.Cell;
import sheet.cell.impl.CellImpl;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.layout.api.Layout;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SheetImpl implements Sheet {

    private static int numberOfSheets = 1;

    private final String name;
    private final Layout layout;
    private int version;
    private final Map<Coordinate, Cell> activeCells;

    private SheetImpl(String name, Layout layout, int version, Map<Coordinate, Cell> activeCells) {

        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (layout == null) {
            throw new IllegalArgumentException("Layout cannot be null");
        }

        if (activeCells == null) {
            throw new IllegalArgumentException("Cells cannot be null");
        }

        this.name = name;
        this.layout = layout;
        setVersion(version);
        this.activeCells = activeCells;
    }

    public static SheetImpl create(String name, Layout layout, int version, Map<Coordinate, Cell> activeCells) {
        numberOfSheets++;
        return new SheetImpl(name, layout, version, activeCells);
    }

    public static SheetImpl create(Layout layout, int version, Map<Coordinate, Cell> activeCells) {
        return new SheetImpl("Sheet" + numberOfSheets, layout, version, activeCells);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Layout getLayout() {
        return this.layout;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(int version) {

        if (!isValidVersion(version)) {
            throw new IllegalArgumentException("Version cannot be less than 1");
        }

        this.version = version;
    }

    @Override
    public Cell getCell(int row, int column) {

        if (isRowInSheetBoundaries(row)) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        }

        if (isColumnInSheetBoundaries(column)) {
            throw new IndexOutOfBoundsException("Column out of bounds");
        }

        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
    }

    @Override
    public Map<Coordinate, Cell> getActiveCells() {
        return Collections.unmodifiableMap(this.activeCells);
    }

    @Override
    public void setCell(int row, int column, String originalValue) {

        if (isRowInSheetBoundaries(row)) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        }

        if (isColumnInSheetBoundaries(column)) {
            throw new IndexOutOfBoundsException("Column out of bounds");
        }

        Optional
                .ofNullable(getCell(row, column))
                .ifPresentOrElse(cell -> cell.setOriginalValue(originalValue),
                        () -> activeCells.put(CoordinateFactory.createCoordinate(row, column),
                                CellImpl.create(CoordinateFactory.createCoordinate(row, column),
                                        this.version,
                                        originalValue)));
    }

    private boolean isRowInSheetBoundaries(int row) {
        return !(row >= this.layout.GetRows());
    }

    private boolean isColumnInSheetBoundaries(int column) {
        return !(column >= this.layout.GetColumns());
    }

    public static boolean isValidVersion(int version) {
        return version >= 1;
    }
}
