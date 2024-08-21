package sheet.cell.impl;

import expression.api.Data;
import expression.api.Expression;
import expression.parser.CellValueParser;
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
    private final Set<Cell> dependentCells;
    private final Set<Cell> influencedCells;

    private CellImpl(Coordinate coordinate, int version, String originalValue) {

        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate cannot be null");
        }

        this.coordinate = coordinate;
        setVersion(version);
        setOriginalValue(originalValue);
        this.dependentCells = new HashSet<>();
        this.influencedCells = new HashSet<>();
    }
    // a2.addDepe

    public static CellImpl create(Coordinate coordinate, int version, String originalValue) {
        return new CellImpl(coordinate, version, originalValue);
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
    public Data getEffectiveValue() {
        return this.effectiveValue;
    }

    @Override
    public Set<Cell> getDependentCells() {
        return Collections.unmodifiableSet(this.dependentCells);
    }

    @Override
    public Set<Cell> getInfluencedCells() {
        return Collections.unmodifiableSet(this.influencedCells);
    }

    @Override
    public void setOriginalValue(String originalValue) {

        Expression exp = CellValueParser.toExpression(originalValue);

//        if (!isValidOriginalValue(originalValue)) {
//            throw new IllegalArgumentException("Invalid original value");
//        }

        Data effectiveValue = exp.evaluate();

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

    private static boolean isValidVersion(int version) {
        return version >= 1;
    }

    private static boolean isValidOriginalValue(String originalValue) {
        // TODO: String originalValue validation.
        return false;
    }

    private static Data calcEffectiveValue(String originalValue) {
        // TODO: 1. Make OriginalValueTree from String cellOriginalValue
        // Todo: 2. Make Data from OriginalValueTree.
        return null;
    }
}
