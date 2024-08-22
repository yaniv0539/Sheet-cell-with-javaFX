package sheet.impl;

import expression.api.Data;
import expression.impl.Ref;
import expression.parser.OrignalValueUtilis;
import sheet.api.CellLookupService;
import sheet.api.Sheet;
import sheet.cell.api.Cell;
import sheet.cell.impl.CellImpl;
import sheet.coordinate.api.Coordinate;
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

    @Override
    public Cell getCell(Coordinate coordinate) {

        if (!isCoordinateInBoundaries(coordinate)) {
            throw new IndexOutOfBoundsException(coordinate + "is not in boundaries");
        }

        return activeCells.get(coordinate);
    }

    @Override
    public Map<Coordinate, Cell> getActiveCells() {
        return Collections.unmodifiableMap(this.activeCells);
    }

    // FOR INTERFACE lookupCellService
    @Override
    public Data getCellData(String cellId) {
        Coordinate c = CoordinateImpl.toCoordinate(cellId);
        Cell cell = activeCells.get(c);

        if (cell == null) {
            throw new IllegalArgumentException(cellId + "is Empty, cannot get data");
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
    public void setCell(Coordinate coordinate, String originalValue) {
        Ref.sheetView = this;
        //validation on Coordinate by format and  on sheet layout

        if (!isCoordinateInBoundaries(coordinate)) {
            throw new IndexOutOfBoundsException(coordinate + "is not in boundaries");
        }

        //coordinate is valid

        CellImpl updatedCell = CellImpl.create(coordinate, version++, originalValue);
        updatedCell.setInfluenceFrom(CellIdToCell(OrignalValueUtilis.findInfluenceFrom(originalValue)));

        if(isCircle(updatedCell)) {
            //throw somthing
            throw  new IllegalArgumentException("circle");
        }
        //check for circle
        //handle error

        Cell previousCell = activeCells.put(coordinate, updatedCell);
        //pass this line inout i valid only localy on cell.

        //if it is a new cell there is no influenceOn, if exist he may have influenced on other cells.
        if(previousCell != null) {
            updatedCell.setInfluenceOn(previousCell.getInfluenceOn());
            try
            {
                recalculationRouteFrom(updatedCell);
            }
            catch(Exception someException)
            {
                //doing rollback to previous sheet.
                activeCells.put(coordinate, previousCell);
                recalculationRouteFrom(previousCell);
                throw  new IllegalArgumentException("re-compute didnt work");
            }

        }
        else {
            for(Cell cell : updatedCell.getInfluenceFrom()) {
                cell.addInfluenceOn(updatedCell);
            }
        }
    }

    @Override
    public void setCells(Map<Coordinate, String> originalValues) {

        Map<Coordinate, Boolean> flagMap = new HashMap<>();
        Map<Coordinate, String> oldOriginalValueMap = new HashMap<>();
        Stack<Coordinate> updatedCellsCoordinates = new Stack<>();

        for (Coordinate coordinate : originalValues.keySet()) {
            flagMap.put(coordinate, false);
        }

        try {
            originalValues.forEach((coordinate, originalValue) -> setCellsHelper(originalValues, flagMap, oldOriginalValueMap, updatedCellsCoordinates, coordinate));
        } catch (Exception exception) {
            updatedCellsCoordinates.forEach(coordinate -> {
                if (oldOriginalValueMap.containsKey(coordinate) && oldOriginalValueMap.get(coordinate).equals(originalValues.get(""))) {
                    this.activeCells.remove(coordinate);
                }
                else {
                    setCell(coordinate, oldOriginalValueMap.get(coordinate));
                }
            });
        }

    }

    private void setCellsHelper(Map<Coordinate, String> newOriginalValuesMap,
                                Map<Coordinate, Boolean> flageMap,
                                Map<Coordinate, String> oldOriginalValueMap,
                                Stack<Coordinate> updatedCellsCoordinates,
                                Coordinate coordinate) {

        if (flageMap.get(coordinate)) {
            return;
        }

        flageMap.put(coordinate, true);

        String newOriginalValue = newOriginalValuesMap.get(coordinate);

        Set<Coordinate> refCoordinates = OrignalValueUtilis.findInfluenceFrom(newOriginalValue);

        refCoordinates.forEach(refCoordinate -> {
            if (this.activeCells.containsKey(refCoordinate)) {
                setCellsHelper(newOriginalValuesMap, flageMap, oldOriginalValueMap, updatedCellsCoordinates, refCoordinate);
            }
            else if (newOriginalValuesMap.containsKey(refCoordinate)) {
                // TODO
            }
            else {
                throw new IndexOutOfBoundsException(refCoordinate + "is not in boundaries");
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

    private boolean isCoordinateInBoundaries(Coordinate coordinate) {
        return coordinate != null && coordinate.getRow() <= this.layout.GetRows() && coordinate.getCol() <= this.layout.GetColumns();
    }

    public static boolean isValidVersion(int version) {
        return version >= 1;
    }

    //function for cell update including rollback
    public boolean recalculationRouteFrom(Cell targetToStart){
        if(targetToStart.getInfluenceOn().isEmpty()){
            targetToStart.computeEffectiveValue();
        }

        for (Cell cell : targetToStart.getInfluenceOn()) {
            recalculationRouteFrom(cell);
        }

        return true;
    }

    private boolean isCircle(CellImpl cellToCheck)
    {
        return cellToCheck.hasCircle();
    }

    private Set<Cell> CellIdToCell(Set<String> newInfluenceCellsId) {
        Set<Cell> cells = new HashSet<>();

        for (String cellId : newInfluenceCellsId) {
            Coordinate coordinate = CoordinateImpl.toCoordinate(cellId);
            cells.add(getCell(coordinate));
        }

        return cells;
    }

    public void updateCell(String coordinateStr, String input) {
    }
}
