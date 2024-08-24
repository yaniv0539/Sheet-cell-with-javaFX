package sheet.impl;

import expression.api.Data;
import expression.impl.Ref;
import expression.parser.OrignalValueUtilis;
import sheet.api.Sheet;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.cell.impl.CellImpl;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.api.CoordinateGetters;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.layout.api.Layout;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class SheetImpl implements Sheet, Serializable {

    private static int numberOfSheets = 1;

    private final String name;
    private final Layout layout;
    private int version;
    private final Map<Coordinate, Cell> activeCells;
    private int numberOfCellsThatChangedSinceCreated;

    private SheetImpl(String name, Layout layout) {

        if (name == null) {
            throw new IllegalArgumentException("Sheet name cannot be null");
        }

        if (layout == null) {
            throw new IllegalArgumentException("Sheet layout cannot be null");
        }

        numberOfSheets++;

        this.name = name;
        this.layout = layout;
        this.version = 1;
        this.activeCells = new HashMap<>();
        this.numberOfCellsThatChangedSinceCreated = 0;
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

    @Override
    public Cell getCell(Coordinate coordinate) {

        if (!isCoordinateInBoundaries(coordinate)) {
            throw new IndexOutOfBoundsException("coordinate " + coordinate + " is not in sheet boundaries");
        }

        return activeCells.get(coordinate);
    }

    @Override
    public Map<CoordinateGetters, CellGetters> getActiveCells() {
        return Collections.unmodifiableMap(this.activeCells);
    }

    @Override
    public int getNumberOfCellsThatChangedSinceCreated() {
        return this.numberOfCellsThatChangedSinceCreated;
    }

    // FOR INTERFACE lookupCellService
    @Override
    public Data getCellData(String cellId) {
        Coordinate coordinate = CoordinateFactory.toCoordinate(cellId);
        Cell cell = activeCells.get(coordinate);

        if (cell == null) {
            throw new IllegalArgumentException("cell" + cellId + "is empty. Cannot get data.");
        }

        return cell.getEffectiveValue();
    }

    @Override
    public void setVersion(int version) {

        if (!isValidVersion(version)) {
            throw new IllegalArgumentException("version cannot be less than 1");
        }

        this.version = version;
    }

    @Override
    public void setCell(Coordinate coordinate, String originalValue) {

        // Gives "Ref" expression permissions to view some data from this sheet.
        Ref.sheetView = this;

        // Validate if the coordinate in sheet boundaries.
        if (!isCoordinateInBoundaries(coordinate)) {
            throw new IndexOutOfBoundsException(coordinate + "is not in boundaries");
        }

        // Creates new cell with its new original value.
        CellImpl updatedCell = CellImpl.create(coordinate, version++, originalValue);

        // Extracts the cells inside the Refs in the original value,
        // and updates the new cell with its new cells he depends on.
        Set<Cell> cells = OrignalValueUtilis.findInfluenceFrom(originalValue).stream().map(this::getCell).collect(Collectors.toSet());
        updatedCell.setInfluenceFrom(cells);

        // Validate if there is a circle.
        if (isCircle(updatedCell)) {
            throw new IllegalArgumentException("illegal cell update, there is a circle!");
        }

        // Adds the new cell and saves the previous cell.
        Cell previousCell = activeCells.put(coordinate, updatedCell);

        // TODO: What this line means? ->
        //pass this line inout i valid only localy on cell.

        // If the previous cell wasn't in 'activeCells' map, we can be sure that other cells were not influenced from it.
        // So, if the previous cell was not exist (null), we'll just update the 'influenceOn' set in cells that it depends on that we are here.
        // Else, we'll update the new cell 'influenceOn' Set with the previous cell 'influenceOn' Set.

        // TODO: To Itay, shouldn't we update the cells that depend on that we are here also if the previous cell is not null?
        // TODO: How can I know if the cells that depends on the new cell is OK with the change?
        // TODO: Why do we need to recalculate the route from our new cell?

        if (previousCell != null) {
            updatedCell.setInfluenceOn(previousCell.getInfluenceOn());
            try
            {
                RecalculationRouteFrom(updatedCell);
            } catch (Exception someException)
            {
                // Rollback to previous sheet.
                activeCells.put(coordinate, previousCell);
                RecalculationRouteFrom(previousCell);
                throw new IllegalArgumentException("illegal cell update, re-compute didn't work");
            }

        }
        else {
            for(Cell cell : updatedCell.getInfluenceFrom()) {
                cell.addInfluenceOn(updatedCell);
            }
        }

        // Cell updated successfully!
        this.numberOfCellsThatChangedSinceCreated++;
    }

    @Override
    public void setCells(Map<Coordinate, String> originalValues) {

        // Preparing flags map so will be able to know if we've been already tried to set a specific cell.
        Map<Coordinate, Boolean> flagMap = new HashMap<>();

        // Preparing old original values map so if we'll have an exception while we'll try to set cells,
        // we'll be able to roll back and redo the operation.
        Map<Coordinate, String> oldOriginalValueMap = new HashMap<>();

        // Preparing a stack of coordinates, so we'll be able to know who is the first and who is the last we updated.
        // The stack will help us to redo if needed.
        Stack<Coordinate> updatedCellsCoordinates = new Stack<>();

        // Initialize the flags map with false values.
        for (Coordinate coordinate : originalValues.keySet()) {
            flagMap.put(coordinate, false);
        }

        try {
            // Sending each original value to helper function.
            originalValues.forEach((coordinate, originalValue) -> setCellsHelper(originalValues, flagMap, oldOriginalValueMap, updatedCellsCoordinates, coordinate));
        } catch (Exception exception) {
            // Undo the operation and move on the exception.
            updatedCellsCoordinates.forEach(coordinate -> {
                if (oldOriginalValueMap.containsKey(coordinate) && "".equals(oldOriginalValueMap.get(coordinate))) {
                    this.activeCells.remove(coordinate);
                }
                else {
                    redoSetCell(coordinate, oldOriginalValueMap.get(coordinate));
                }
            });

            throw exception;
        }
    }

    private void setCellsHelper(Map<Coordinate, String> newOriginalValuesMap,
                                Map<Coordinate, Boolean> flagMap,
                                Map<Coordinate, String> oldOriginalValueMap,
                                Stack<Coordinate> updatedCellsCoordinates,
                                Coordinate coordinate) {

        // If we touched the coordinate, go back.
        if (flagMap.get(coordinate)) {
            return;
        }

        // We touched the coordinate!
        flagMap.put(coordinate, true);

        String newOriginalValue = newOriginalValuesMap.get(coordinate);

        Set<Coordinate> refCoordinates = OrignalValueUtilis.findInfluenceFrom(newOriginalValue);

        // For each cell that we might be depended on, we'll do some checks:
        // If the cell is inside 'newOriginalValuesMap', we'll do recursive operation with this cell.
        // Else if the cell is not inside 'activeCells' map we'll throw exception because this cell is null.
        // Else, it is inside 'activeCells' map, and we'll skip to next iteration.

        refCoordinates.forEach(refCoordinate -> {
            if (newOriginalValuesMap.containsKey(refCoordinate)) {
                setCellsHelper(newOriginalValuesMap, flagMap, oldOriginalValueMap, updatedCellsCoordinates, refCoordinate);
            }
            else if (!this.activeCells.containsKey(refCoordinate)) {
                throw new IndexOutOfBoundsException(refCoordinate + " is empty, cannot get data");
            }
        });

        if (this.activeCells.containsKey(coordinate)) {
            Cell cell = this.activeCells.get(coordinate);
            oldOriginalValueMap.put(coordinate, cell.getOriginalValue());
        }

        else {
            oldOriginalValueMap.put(coordinate, "");
        }

        setCell(coordinate, newOriginalValue);
        updatedCellsCoordinates.push(coordinate);
    }

    private void redoSetCell(Coordinate coordinate, String originalValue)
    {
        this.numberOfCellsThatChangedSinceCreated -= 2;
        setCell(coordinate, originalValue);
    }

    private boolean isCoordinateInBoundaries(Coordinate coordinate) {
        return !(coordinate == null || coordinate.getRow() > this.layout.getRows() || coordinate.getCol() > this.layout.getColumns());
    }

    public static boolean isValidVersion(int version) {
        return version >= 1;
    }

    //function for cell update including rollback
    public boolean RecalculationRouteFrom(Cell targetToStart) {
        if (targetToStart.getInfluenceOn().isEmpty()) {
            targetToStart.computeEffectiveValue();
        }

        for (Cell cell : targetToStart.getInfluenceOn()) {
            RecalculationRouteFrom(cell);
        }

        return true;
    }

    private boolean isCircle(CellImpl cellToCheck)
    {
        return cellToCheck.HasCircle();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
