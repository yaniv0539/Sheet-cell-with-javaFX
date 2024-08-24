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

         isCoordinateInSheetBoundaries(target);
         Cell updatedCell = CellImpl.create(target, version++, originalValue);
         Cell previousCell =  insertCellToSheet(updatedCell);

         if (previousCell != null) {

             try {
                 recalculateSheetFrom(updatedCell);
             }
             catch(Exception someException)
             {
                    //doing rollback to previous sheet.
                 insertCellToSheet(previousCell);
                 recalculateSheetFrom(updatedCell);

                 throw new IllegalArgumentException("recalculation didnt work");
             }
         }
    }

    private boolean isCoordinateInSheetBoundaries(Coordinate target) {

        if(!isRowInSheetBoundaries(target.getRow()) || !isColumnInSheetBoundaries(target.getCol())) {
            throw new IllegalArgumentException("Row or column out of bounds !");
        }

        return true;
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

    private void circleFrom(Cell cellToCheck) {
        if(hasCircle(cellToCheck)) {
            //throw somthing
            throw  new IllegalArgumentException("circle");
        }
    }

    private boolean hasCircle(Cell cellToCheck) {
        return recHasCircle(cellToCheck, new HashSet<Coordinate>());
    }

    private boolean recHasCircle(Cell current, Set<Coordinate> visited) {
        // If the current object is already visited, a cycle is detected
        if (visited.contains(current.getCoordinate())) {
            return true;
        }

        // Mark the current object as visited
        visited.add(current.getCoordinate());

        // Recur for all the objects in the relatedObjects list
        for (Cell affectedBy : current.getInfluenceFrom()) {
            // If a cycle is detected in the recursion, return true
            if (recHasCircle(affectedBy, visited)) {
                return true;
            }
        }

        // Remove the current object from the visited set (backtracking)
        visited.remove(current.getCoordinate());

        // If no cycle was found, return false
        return false;
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

    private void dfs(Cell cell, Set<Coordinate> visited,Stack<Cell> stack) {
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

    private Cell insertCellToSheet(Cell toInsert) {

        Cell toReplace = activeCells.put(toInsert.getCoordinate(),toInsert);

        toInsert.setInfluenceFrom(CoordinateToCell(OrignalValueUtilis.findInfluenceFrom(toInsert.getOriginalValue())));
        toInsert.getInfluenceFrom().forEach(cell -> cell.getInfluenceOn().add(toInsert));

        //if it is a new cell there is no influenceOn, if exist he may have influenced on other cells.
        if(toReplace != null) {
            toInsert.setInfluenceOn(toReplace.getInfluenceOn());
            toInsert.getInfluenceOn().forEach(cell -> cell.getInfluenceFrom().add(toInsert));
            //until here we get a new sheet now we just need to remove
            toReplace.getInfluenceFrom().forEach(cell -> cell.getInfluenceOn().remove(toReplace));
            toReplace.getInfluenceOn().forEach(cell -> cell.getInfluenceFrom().remove(toReplace));

        }

        return toReplace;
    }

    private void recalculateSheetFrom(Cell cell) {
        circleFrom(cell);
        Stack<Cell> cellStack = topologicalSortFrom(cell);
        //compute
        while (!cellStack.isEmpty()) {
            cellStack.pop().computeEffectiveValue();
        }
    }

}
