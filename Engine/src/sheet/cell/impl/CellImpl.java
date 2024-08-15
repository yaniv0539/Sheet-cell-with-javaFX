package sheet.cell.impl;

import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class CellImpl implements Cell{

    private final Coordinate coordinate;
    private int version;
    private String originalValue;
    private EffectiveValue effectiveValue;
    private final Set<Cell> dependentCells;
    private final Set<Cell> influencedCells;

    public CellImpl(Coordinate coordinate, int version, String originalValue) {
        this.coordinate = coordinate;
        this.version = version;
        this.originalValue = originalValue;
        this.dependentCells = new HashSet<>();
        this.influencedCells = new HashSet<>();
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public String getOriginalValue() {
        return this.originalValue;
    }

    @Override
    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public EffectiveValue getEffectiveValue() {
        return this.effectiveValue;
    }

    @Override
    public Set<Cell> getDependentCells() {
        return this.dependentCells;
    }

    @Override
    public Set<Cell> getInfluencedCells() {
        return this.influencedCells;
    }

    // REF(a5.expression, a4.expression)
    // a3.setValue(string)
}
