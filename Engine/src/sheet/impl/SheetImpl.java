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

import java.util.*;

public class SheetImpl implements Sheet, CellLookupService {

    private static int numberOfSheets = 1;

    private final String name;
    private final Layout layout;
    private int version;
    private Map<Coordinate, Cell> activeCells;

    private SheetImpl(String name, Layout layout) {

        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        if (layout == null) {
            throw new IllegalArgumentException("Layout cannot be null");
        }

        numberOfSheets++;

        this.name = name;
        this.layout = layout;
        this.version = 1;
        this.activeCells = new HashMap<>();
    }

    public static SheetImpl create(String name, Layout layout) {

        return new SheetImpl(name, layout);
    }

    public static SheetImpl create(Layout layout) {
        return new SheetImpl("Sheet" + numberOfSheets, layout);
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


    // FOR INTERFACE lookupCellService
    @Override
    public Data getCellData(String cellId) {
        Coordinate c = CoordinateImpl.toCoordinate(cellId);
        Cell cell = activeCells.get(c);

        if (cell == null) {
            throw new IllegalArgumentException(cellId);
        }

        return cell.getEffectiveValue();
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

        if (!isRowInSheetBoundaries(row)) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        }

        if (!isColumnInSheetBoundaries(column)) {
            throw new IndexOutOfBoundsException("Column out of bounds");
        }

        return activeCells.get(CoordinateFactory.createCoordinate(row, column));
    }

    @Override
    public Map<Coordinate, Cell> getActiveCells() {
        return Collections.unmodifiableMap(this.activeCells);
    }

    @Override
    public void setCell(Coordinate coordinate, String originalValue) {

        Ref.sheetView = this;

//        if (!isRowInSheetBoundaries(row)) {
//            throw new IndexOutOfBoundsException("Row out of bounds");
//        }
//
//        if (!isColumnInSheetBoundaries(column)) {
//            throw new IndexOutOfBoundsException("Column out of bounds");
//        }

        activeCells.put(coordinate,CellImpl.create(coordinate, version, originalValue));
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
