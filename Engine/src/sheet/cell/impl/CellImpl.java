package sheet.cell.impl;

import expression.api.Data;
import expression.parser.OrignalValueUtilis;
import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CellImpl implements Cell {

    private final Coordinate coordinate;
    private int version;
    private String originalValue;
    private Data effectiveValue;
    private Set<Cell> influenceFrom;
    private Set<Cell> influenceOn;

    private CellImpl(Coordinate coordinate, int version, String originalValue) {

        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate cannot be null");
        }

        this.coordinate = coordinate;
        setVersion(version);
        SetOriginalValue(originalValue);
        this.influenceFrom = new HashSet<>();
        this.influenceOn = new HashSet<>();
    }

    public static CellImpl create(Coordinate coordinate, int version, String originalValue) {
        return new CellImpl(coordinate, version, originalValue);
    }

    @Override
    public Coordinate GetCoordinate() {
        return this.coordinate;
    }

    @Override
    public int GetVersion() {
        return this.version;
    }

    @Override
    public String GetOriginalValue() {
        return this.originalValue;
    }

    @Override
    public Data GetEffectiveValue() {
        return this.effectiveValue;
    }

    @Override
    public Set<Cell> GetInfluenceFrom() { return Collections.unmodifiableSet(this.influenceFrom); }

    @Override
    public Set<Cell> GetInfluenceOn() {
        return Collections.unmodifiableSet(this.influenceOn);
    }

    @Override
    public void SetInfluenceOn(Set<Cell> influenceOn) {
        this.influenceOn = influenceOn;
    }

    @Override
    public void SetInfluenceFrom(Set<Cell> influenceFrom) {
        this.influenceFrom = influenceFrom;
    }

    @Override
    public void AddInfluenceOn(Cell effectOn) {
        influenceOn.add(effectOn);
    }

    @Override
    public void AddInfluenceFrom(Cell AffectedFrom) { influenceFrom.add(AffectedFrom); }

    @Override
    public void SetOriginalValue(String originalValue) {

        Data effectiveValue = OrignalValueUtilis.toExpression(originalValue).Evaluate();
        //getting data, if pass this line value is valid to this specific cell.
        //the sheet need to check if this is ok for all cells that depend on this cell data.
        //get the cell that "this" influence from

        this.originalValue = originalValue;
        setEffectiveValue(effectiveValue);
    }

    private void setEffectiveValue(Data effectiveValue) {
        this.effectiveValue = effectiveValue;
    }

    private void setVersion(int version) {

        if (!isValidVersion(version)) {
            throw new IllegalArgumentException("Version cannot be less than 1");
        }

        this.version = version;
    }

    @Override
    public void ComputeEffectiveValue() { SetOriginalValue(this.originalValue); }

    public boolean HasCircle()
    {
        return recHasCircle(this, new HashSet<Coordinate>());
    }

    private boolean recHasCircle(Cell current, Set<Coordinate> visited) {
        // If the current object is already visited, a cycle is detected
        if (visited.contains(current.GetCoordinate())) {
            return true;
        }

        // Mark the current object as visited
        visited.add(current.GetCoordinate());

        // Recur for all the objects in the relatedObjects list
        for (Cell affectedBy : current.GetInfluenceFrom()) {
            // If a cycle is detected in the recursion, return true
            if (recHasCircle(affectedBy, visited)) {
                return true;
            }
        }

        // Remove the current object from the visited set (backtracking)
        visited.remove(current.GetCoordinate());

        // If no cycle was found, return false
        return false;
    }

    private static boolean isValidVersion(int version) {
        return version >= 1;
    }

    @Override
    public String toString() {
        return this.coordinate.toString();
    }
}
