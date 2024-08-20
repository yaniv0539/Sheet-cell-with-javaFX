package sheet.impl;

import expression.api.Data;
import expression.impl.Ref;
import sheet.api.CellLookupService;
import sheet.api.Sheet;
import sheet.cell.api.Cell;
import sheet.cell.impl.CellImpl;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;
import sheet.layout.api.Layout;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SheetImpl implements Sheet, CellLookupService {

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


    //for ref
    @Override
    public Data getCellData(String cellId) {

        return activeCells.get(CoordinateImpl.toCoordinate(cellId)).getEffectiveValue();
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

        Ref.sheetView = this;
        if (isRowInSheetBoundaries(row)) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        }

        if (isColumnInSheetBoundaries(column)) {
            throw new IndexOutOfBoundsException("Column out of bounds");
        }
        Coordinate i = CoordinateFactory.createCoordinate(row, column);

        activeCells.put(i,CellImpl.create(i,version, originalValue));
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
