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

        if (!isRowInSheetBoundaries(coordinate.getRow())) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        }

        if (!isColumnInSheetBoundaries(coordinate.getCol())) {
            throw new IndexOutOfBoundsException("Column out of bounds");
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
    public void setCell(Coordinate target, String originalValue) {
        Ref.sheetView = this;

        if(!isRowInSheetBoundaries(target.getRow()) || !isColumnInSheetBoundaries(target.getCol())) {
            throw new IllegalArgumentException("Row or column out of bounds !");
        }

        CellImpl updatedCell = CellImpl.create(target, version++, originalValue);
        updatedCell.setInfluenceFrom(CoordinateToCell(OrignalValueUtilis.findInfluenceFrom(originalValue)));

        if(isCircle(updatedCell)) {
            //throw somthing
            throw  new IllegalArgumentException("circle");
        }

        Cell previousCell = activeCells.put(target,updatedCell);
        //if it is a new cell there is no influenceOn, if exist he may have influenced on other cells.
        if(previousCell != null) {
            updatedCell.setInfluenceOn(previousCell.getInfluenceOn());

            try
            {
                Stack<Cell> stack = topologicalSortFrom(updatedCell);
                recalculationRouteFrom(stack);
            }
            catch(Exception someException)
            {
                //doing rollback to previous sheet.
                activeCells.put(target,previousCell);
                Stack<Cell> stack = topologicalSortFrom(previousCell);
                recalculationRouteFrom(stack);
                throw new IllegalArgumentException("recalculation didnt work");
            }

        }
        else {
            for(Cell cell : updatedCell.getInfluenceFrom()){
                cell.addInfluenceOn(updatedCell);
            }
            //updatedCell.getInfluenceFrom().forEach(cell-> cell.addInfluenceOn(updatedCell));
        }
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

    //function for cell update including rollback
    public boolean recalculationRouteFrom(Stack<Cell> cellStack) {

        // Print the contents of the stack which is the topological order
        while (!cellStack.isEmpty()) {
            cellStack.pop().computeEffectiveValue();
        }

        return true;
    }

    private boolean isCircle(CellImpl cellToCheck)
    {
        return cellToCheck.hasCircle();
    }

    private Set<Cell> CoordinateToCell(Set<Coordinate> newInfluenceCellsId) {
        Set<Cell> Cells = new HashSet<>();

        for (Coordinate location : newInfluenceCellsId) {
            Cells.add(getCell(location));
        }
        return Cells;
    }

    private Stack<Cell> topologicalSortFrom(Cell cell) {

        Stack<Cell> stack = new Stack<>();
        Set<Coordinate> visited = new HashSet<>();

        // Call the recursive helper function to store topological sort starting from all cells one by one
        for (Cell neighbor : cell.getInfluenceOn()) {

            if (!visited.contains(neighbor.getCoordinate())) {
                dfs(neighbor, visited, stack);
            }
        }

        return stack;
    }

    private void dfs(Cell cell, Set<Coordinate> visited,Stack<Cell> stack)
    {
        visited.add(cell.getCoordinate());

        // Visit all the adjacent vertices
        for (Cell neighbor : cell.getInfluenceOn()) {

            if (!visited.contains(neighbor.getCoordinate())) {
                dfs(neighbor, visited, stack);
            }
        }

        // Push current cell to stack which stores the result
        stack.push(cell);
    }

}
